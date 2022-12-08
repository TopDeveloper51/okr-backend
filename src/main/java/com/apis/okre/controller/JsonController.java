package com.apis.okre.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.util.Constants;
import com.apis.okre.util.JwtTokenUtil;
import com.apis.okre.util.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * load json file to DB for updating tb_user and tb_department
 * @author alex
 *
 */
@RestController
@SpringBootApplication
@CrossOrigin
@RequestMapping("/ap-service")
public class JsonController {

	@Value("${json.getUser.url}")
	private String userUrl;

	@Value("${json.getDepartment.url}")
	private String departmentUrl;

	@Value("${json.userPath}")
	private String userPath;

	@Value("${json.departmentPath}")
	private String departmentPath;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DepartmentMapper dpMapper;

	@RequestMapping(value = "/loadUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> loadUsers() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(userUrl, String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(result);
			int upd = 0, inst = 0;
			final JsonNode arrNode = rootNode.get("data");
			for (final JsonNode objNode : arrNode) {
				JSONObject jObj1 = new JSONObject(objNode.toString());
				User user = objectMapper.readValue(jObj1.toString(), User.class);
				User checkUser = new User();
				checkUser.id = user.id;
				List<User> tmpUsers = userMapper.selectByFields(checkUser);
				
				user.postNameStr = String.join(",", user.postName);
				
				if (tmpUsers.size() == 0) {
					user.user_role = "user";
					user.user_company_id = jwtTokenUtil.getUserDetailFromTokenPhone().user_company_id;
					user.user_password = jwtTokenUtil.getEncodedDefaultPassword();
					user.user_post_address = user.employeeName + ":" + UUID.randomUUID().toString() + "@virtual.account";
				}
				
				if (tmpUsers.size() > 0) {
					if (tmpUsers.get(0).user_post_address != null) {
						user.user_post_address = null;
					}
					upd += userMapper.updateByFields(user);
				} else {
					inst += userMapper.addOne(user);

				}
			}
			return new ResponseEntity<String>(upd + " updated," + inst + " added", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/loadDepartments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> loadDepartments() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(departmentUrl, String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(result);
			int upd = 0, inst = 0;
			final JsonNode arrNode = rootNode.get("data");
			for (final JsonNode objNode : arrNode) {
				JSONObject jObj1 = new JSONObject(objNode.toString());
				Department dp = objectMapper.readValue(jObj1.toString(), Department.class);
				if (dp.pid == null) {
					dp.pid = jwtTokenUtil.getUserDetailFromTokenPhone().user_company_id;
				}
				Department checkDp = new Department();
				checkDp.id = dp.id;
				if (dpMapper.selectByFields(checkDp).size() > 0) {
					upd += dpMapper.updateByFields(dp);
				} else {
					inst += dpMapper.addOne(dp);
				}
			}

			Department newDp = new Department();
			newDp.id = jwtTokenUtil.getUserDetailFromTokenPhone().user_company_id;
			newDp.dp_serial_id = "";
			dpMapper.updateByFields(newDp);

			newDp.id = null;
			newDp.dp_serial_id = null;
			while (dpMapper.updateByFields(newDp) > 0) {

			}

			return new ResponseEntity<String>(upd + " updated," + inst + " added", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/foreignInterface/queryEmployeInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getUsers() {
		try {
			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			// read customer.json file into tree model
			JsonNode rootNode = objectMapper.readTree(new File(userPath));

			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/foreignInterface/queryDepartmentInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getDepartments() {
		try {
			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			// read customer.json file into tree model
			JsonNode rootNode = objectMapper.readTree(new File(departmentPath));

			return new ResponseEntity<JsonNode>(rootNode, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
}
