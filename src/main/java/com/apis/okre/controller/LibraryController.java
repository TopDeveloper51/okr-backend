package com.apis.okre.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;

import com.apis.okre.service.KrobjectService;
import com.apis.okre.util.Constants;
import com.apis.okre.util.Utils;
import com.fasterxml.jackson.databind.JsonNode;
/**
 * table: tb_object
 * @author alex
 * option: ob_company_id = 0
 */
@RestController
@SpringBootApplication
@CrossOrigin
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	private KrobjectService objectService;
	
	@Autowired
	private KrobjectMapper obMapper;
	
	@Autowired
	private KresultMapper krMapper;
	
	@Autowired
	private TaskMapper taskMapper;
	
	@RequestMapping(value = "/getLibraryMenu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getLibraryMenu() {
		try {
			List<Krobject> list  = obMapper.getLibraryMenu();
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, list).toString(),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/getLibraryByCriteria", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getLibraryByCriteria(@RequestBody Krobject param) {
		try {
			List<Krobject> list  = obMapper.getLibraryByCriteria(param);
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, list).toString(),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/addLibraryMenuByJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addLibraryMenuByJson(@RequestBody JsonNode param) {
		try {
			final JsonNode arrNode = param.get("Data");
			if (arrNode.isArray()) {
			    for (final JsonNode objNode : arrNode) {
			    	JSONObject jObj  = new JSONObject(objNode.toString());
			        KrobjectReq menuKrobj = new KrobjectReq();
			        menuKrobj.ob_name = jObj.getString("name");
			        menuKrobj.ob_company_id = 0L;
			        obMapper.addObject(menuKrobj);
			    }
			}
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_SUCCESSFULLY, param).toString(),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/addLibraryByJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addLibraryByJson(@RequestBody JsonNode param) {
		try {
			final JsonNode arrNode = param.get("Data");
			if (arrNode.isArray()) {
			    for (final JsonNode objNode : arrNode) {
			    	JSONObject jObj  = new JSONObject(objNode.toString());
			        
			        Krobject obParam = new Krobject();
			        obParam.ob_name = jObj.getString("parentName");
			        Krobject parentKrobj = obMapper.getAllObjectByCriteria(obParam).get(0);
			        
			        obParam = new Krobject();
			        obParam.ob_parent_object = parentKrobj.ob_id;
			        obParam.ob_name = jObj.getString("name");
			        if(obMapper.getAllObjectByCriteria(obParam).size()==0) {
			        	KrobjectReq newKrobj = new KrobjectReq();
				        newKrobj.ob_name = jObj.getString("name");
				        newKrobj.ob_company_id = 0L;
				        newKrobj.ob_parent_object = parentKrobj.ob_id;
				        obMapper.addObject(newKrobj);
				        final JsonNode arrNode1 = objNode.get("subelements");
				        for (final JsonNode krNode : arrNode1) {
				        	JSONObject jObj1  = new JSONObject(krNode.toString());
				        	Kresult newKr = new Kresult();
				        	newKr.kr_name = jObj1.getString("name");
				        	newKr.kr_parent_object = newKrobj.ob_id;
				        	krMapper.addKresult(newKr);
				        	final JsonNode arrNode2 = krNode.get("subelements");
					        for (final JsonNode taskNode : arrNode2) {
					        	JSONObject jObj2  = new JSONObject(taskNode.toString());
					        	Otask newTask = new Otask();
					        	newTask.task_name = jObj2.getString("name");
					        	newTask.task_parent_kr = newKr.kr_id;
					        	taskMapper.addOne(newTask);
					        }
				        }
			        }
			    }
			}
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_SUCCESSFULLY, param).toString(),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

//	@RequestMapping(value = "/addObjectKr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public ResponseEntity<?> addObjectKr(@RequestBody Krobject param) {
//		try {
//			Long ret = objectService.addObjectKr(param);
//			if (ret == 0) {
//				return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_FAILED).toString(),
//						HttpStatus.BAD_REQUEST);
//			} else {
//				Krobject newObj = new Krobject();
//				newObj.ob_id = param.ob_id;
//				List<Krobject> retObj = objectService.getAllObjectByCriteria(newObj);
//				return new ResponseEntity<String>(
//						Utils.getResponseObject(Constants.INSERT_SUCCESSFULLY, retObj.get(0)).toString(),
//						HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<String>(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
//		}
//
//	}

}
