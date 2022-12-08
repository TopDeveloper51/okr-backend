package com.apis.okre.controller;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.Constants;
import com.apis.okre.util.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * controller of Dashboard and item's Dashboard
 * @author alex
 *
 */
@RestController
@SpringBootApplication
@CrossOrigin
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardMapper dbMapper;
	
	@RequestMapping(value = "/getAnalyzeScoreObjects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectAnalyzeScoreObjects(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<Krobject> res1 = dbMapper.selectAnalyzeScoreObjects(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/getItemResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectItemResult(@RequestBody Dashboard param) {
		try {
			JSONObject resJson = new JSONObject();

			List<TaskMilestone> res1= dbMapper.selectItemMsResult(param);
		    ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.valueToTree(res1);
			resJson.put("selectItemMsResult",new JSONArray(node.toString()));
			
			List<TaskOwner> res2= dbMapper.selectItemOwnerResult(param);
			node = objectMapper.valueToTree(res2);
			resJson.put("selectItemOwnerResult",new JSONArray(node.toString()));

			int[] res3 = dbMapper.selectItemTaskResult(param);
			resJson.put("selectItemTaskResult", res3);
			
			return new ResponseEntity<String>(
					Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, resJson).toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * getAnalyze
	 * @param param
	 * @return
	 * // "OKRScoreCounts": [
	*             23,          * all
	*             0,           * 未评分
	*             11,          * 0-0.3分
	*             7,           * 0.4-0.7分
	*             5            * 0.8-1分
	*         ],
	 */
	@RequestMapping(value = "/getAnalyze", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectAnalyzeConstants(@RequestBody Dashboard param) {
		try {
			JSONObject resJson = new JSONObject();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    LocalDateTime now = LocalDateTime.now();
		    
		    List<ObjectProgress> res1= dbMapper.selectAnalyzeDpObjectProgress(param);
		    ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.valueToTree(res1);
			resJson.put("OKRDepartmentProgress",new JSONArray(node.toString()));
		    
			List<ObjectScore> res4= dbMapper.selectAnalyzeDpObjectScore(param);
			JsonNode node1 = objectMapper.valueToTree(res4);
			resJson.put("OKRDepartmentScore",new JSONArray(node1.toString()));
			
			
			
		   	param.req_date= dtf.format(now);
			param.req_dp_serial = param.req_dp_serial + ".%";
			param.req_index = -1;
			ObjectProgress res2= dbMapper.selectTrackObjectProgress(param);
			resJson.put("OKRAverageProgress", res2.result);
			
			int[] res3 = dbMapper.selectAnalyzeScoreCounts(param);
			resJson.put("OKRScoreCounts", res3);
			
			return new ResponseEntity<String>(
					Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, resJson).toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/getTracking", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectTrackConstants(@RequestBody Dashboard param) {
		try {
			JSONObject resJson = new JSONObject();
			param.req_dp_serial = param.req_dp_serial + ".%";

			int[] res1 = dbMapper.selectTrackConstants(param);
			resJson.put("trackValues", res1);
			
			return new ResponseEntity<String>(
					Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, resJson).toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/getTrackObjectProgress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectTrackObjectProgress(@RequestBody Dashboard param) {
		try {
			JSONObject resJson = new JSONObject();
			param.req_dp_serial = param.req_dp_serial + ".%";
			List<ObjectProgress> res1 = new ArrayList<ObjectProgress> ();
			for(int i=12; i>=0; i--) {
				param.req_index = i;
				ObjectProgress res2 =dbMapper.selectTrackObjectProgress(param);
				res2.result = res2.result /12 *(12-i);
				res1.add( res2 );
			}
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.valueToTree(res1);
			resJson.put("companyProgress",new JSONArray(node.toString()));
			
			param.req_dp_serial = "%";
			List<ObjectProgress> res2 = new ArrayList<ObjectProgress> ();
			for(int i=12; i>=0; i--) {
				param.req_index = i;
				ObjectProgress res21 =dbMapper.selectTrackObjectProgress(param);
				res21.result = res21.result /12 *(12-i);
				res2.add( res21 );
			}
			node = objectMapper.valueToTree(res2);
			resJson.put("allProgress",new JSONArray(node.toString()));
			
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, resJson).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 一周内未更新的E-执行
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getTrackExecuteNotProgress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectTrackExecuteNotProgress(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<Execute> res1 = dbMapper.selectTrackExecuteNotProgress(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 一周内未更新的O
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getTrackObjectNotProgress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectTrackObjectNotProgress(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<Krobject> res1 = dbMapper.selectTrackObjectNotProgress(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  一周内未更新的KR
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getTrackKrNotProgress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectTrackKrNotProgress(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<Kresult> res1 = dbMapper.selectTrackKrNotProgress(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * getSetting
	 * @param param
	 *    	"req_cycle":"%",      // 2021/3
	 * 		"req_dp_serial":".1.624" // dp_serial_id + "." + dp.id
	 * @return
	 *   "settingObjectKr": [     目标 O 下的 KR 数量分布
	*             38,      all          
	*             8,       未设置KR
	*             24,      1-2个KR   
	*             6,       3-5个KR 
	*             0        5个以上KR
	*         ],
	*         "settingRate": [
	*             1.559,        OKR 填写率
	*             0,
	*             13.1579,     OKR 对齐率
	*             0,
	*             5.7692,      E-执行分解率
	*             0
	*         ],
	*         "SettingObjectParent": [     OKR对齐量
	*             6,       已对齐
	*             32,       未对齐
	*             5        有 xxx 个员工未对齐OKR
	*         ],
	*          "SettingkrDissolve": [  KR分解（E-执行）
	*             3,           已分解
	*             49,          未分解
	*             4            有 xxx 个员工未分解KR
	*         ],            
	*         "settingUserObject": [   每个人负责的目标 O 数量分布
	*             449,     all
	*             442,     未设置OKR
	*             6,       1-5个OKR
	*             1        5个以上OKR
	*         ]
	 */
	@RequestMapping(value = "/getSetting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectSettingRate(@RequestBody Dashboard param) {
		try {
			JSONObject resJson = new JSONObject();
			param.req_dp_serial = param.req_dp_serial + ".%";

			float[] res1 = dbMapper.selectSettingRate(param);
			res1[1] = res1[0] - res1[1];
			res1[3] = res1[2] - res1[3];
			res1[5] = res1[4] - res1[5];
			resJson.put("settingRate", res1);

			int[] res2 = dbMapper.selectSettingUserObject(param);
			resJson.put("settingUserObject", res2);

			int[] res3 = dbMapper.selectSettingObjectKr(param);
			resJson.put("settingObjectKr", res3);

			int[] res4 = dbMapper.selectSettingObjectParent(param);
			resJson.put("SettingObjectParent", res4);

			int[] res5 = dbMapper.selectSettingkrDissolve(param);
			resJson.put("SettingkrDissolve", res5);
			
			
			return new ResponseEntity<String>(
					Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, resJson).toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  有 xx 个员工未分解KR
	 * @param param
	 * 		req_cycle:"%",      ex: 2021/3
	 * 		req_dp_serial:".1.624" ex: dp_serial_id + "." + dp.id
	 * 		req_option:"1"        ex: 0,1
	 * @return
	 */
	@RequestMapping(value = "/getSettingkrNotDissolveUsers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectSettingkrNotDissolveUsers(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<User> res1 = dbMapper.selectSettingkrNotDissolveUsers(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  KR分解（E-执行）
	 * @param param
	 * 		req_cycle:"%",      ex: 2021/3
	 * 		req_dp_serial:".1.624" ex: dp_serial_id + "." + dp.id
	 * 		req_option:"1"        ex: 0,1
	 * @return
	 */
	@RequestMapping(value = "/getSettingkrDissolves", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectSettingkrDissolves(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<Kresult> res1 = dbMapper.selectSettingkrDissolves(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  每个人负责的目标 O 数量分布	
	 * @param param
	 * 		req_cycle:"%",      ex: 2021/3
	 * 		req_dp_serial:".1.624" ex: dp_serial_id + "." + dp.id
	 * 		req_option:"1"        ex: 0,1,5
	 * 			未设置OKR   :0
	 * 			1-5个OKR    :1
	 * 			5个以上OKR   :5
	 * @return
	 */
	@RequestMapping(value = "/getSettingUsers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectSettingUserObjects(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<User> res1 = dbMapper.selectSettingUserObjects(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  目标 O 下的 KR 数量分布
	 * @param param
	 * 		req_cycle:"%",      ex: 2021/3
	 * 		req_dp_serial:".1.624" ex: dp_serial_id + "." + dp.id
	 * 		req_option:"3"        ex: 0,1,3,5
	 * 			未设置KR     :0
	 * 			1-2个KR      :1
	 * 			3-5个KR      :3
	 * 			5个以上KR    :5
	 * @return
	 */
	@RequestMapping(value = "/getSettingObjectKrs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectSettingObjectKrs(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<Krobject> res1 = dbMapper.selectSettingObjectKrs(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  OKR对齐量
	 * @param param
	 * 		req_cycle:"%",      ex: 2021/3
	 * 		req_dp_serial:".1.624" ex: dp_serial_id + "." + dp.id
	 * 		req_option:"1"        ex: 0,1
	 * 			已对齐   :1
	 * 			未对齐   :0
	 * @return
	 */
	@RequestMapping(value = "/getSettingObjectParents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectSettingObjectParents(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<Krobject> res1 = dbMapper.selectSettingObjectParents(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  有 xxx 个员工未对齐OKR
	 * @param param
	 * 		req_cycle:"%",      ex: 2021/3
	 * 		req_dp_serial:".1.624" ex: dp_serial_id + "." + dp.id
	 * 		req_option:"1"        ex: 0,1
	 * @return
	 */
	@RequestMapping(value = "/getSettingObjectNotParentUsers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectSettingObjectNotParentUsers(@RequestBody Dashboard param) {
		try {
			param.req_dp_serial = param.req_dp_serial + ".%";

			List<User> res1 = dbMapper.selectSettingObjectNotParentUsers(param);

			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, res1).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
}
