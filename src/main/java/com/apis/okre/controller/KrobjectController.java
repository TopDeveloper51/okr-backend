package com.apis.okre.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
import com.apis.okre.service.KrobjectService;
import com.apis.okre.util.Constants;
import com.apis.okre.util.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * table: tb_object  目标
 * @author alex
 *
 */
@RestController
@SpringBootApplication
@CrossOrigin
@RequestMapping("/object")
public class KrobjectController {

	@Autowired
	private KrobjectService objectService;

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getAllObjectByCriteria(@RequestBody ReqKObjectParam param) {
		try {
			List<Krobject> list = objectService.getAllObjectByCriteria(param.reqObj);

			ResKObjectParam reqObj = new ResKObjectParam();
			reqObj.list = (List<Krobject>) checkPage(param.Pages, list);
			reqObj.Pages = param.Pages;
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, reqObj).toString(),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/list/{objectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOneObjectById(@PathVariable(value = "objectId") String objectId) {
		try {
			Krobject kobj = objectService.getOneObjectById(objectId);
			if (kobj == null) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.BAD_REQUEST).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, kobj).toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/addObjectKr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addObjectKr(@Valid @RequestBody KrobjectReq param, BindingResult bindingResult) {
		try {
			if(bindingResult.hasErrors()) {
				return new ResponseEntity<String>(
						Utils.getResponseObjectError(Constants.VALIDATION_ERROR, bindingResult.getFieldErrors()).toString(),
						HttpStatus.BAD_REQUEST);
			}
			
			Long ret = objectService.addObjectKr(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				Krobject newObj = new Krobject();
				newObj.ob_id = param.ob_id;
				List<Krobject> retObj = objectService.getAllObjectByCriteria(newObj);
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.INSERT_SUCCESSFULLY, retObj.get(0)).toString(),
						HttpStatus.OK);
			}
		

		} catch (Exception e) {
			return new ResponseEntity<String>(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(value = "/updateObject", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateObjectByField(@RequestBody Krobject param) {
		try {
			int ret = objectService.updateObjectByField(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.UPDATE_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			}else if(ret == -1) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.UPDATE_PARENT_OBJECT_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			}else if(ret == -3) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.PERMISSION_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.UPDATE_SUCCESSFULLY, ret).toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/deleteByFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteByFields(@RequestBody Krobject param) {
		try {
			int ret = objectService.deleteByFields(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.DELETE_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			}else if(ret == -1){
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.PERMISSION_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.DELETE_SUCCESSFULLY, ret).toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	public List<?> checkPage(Pagination param, List<?> pList) {
		param.page_totals = pList.size();
		if (param.page_counts == null) {
			return pList;
		} else {
			if (param.page_totals - 1 < param.page_start || param.page_start == null) {
				param.page_start = 0;
			}
			if (param.page_totals < param.page_start + param.page_counts) {
				param.page_counts = Math.min(param.page_totals - param.page_start, param.page_counts);
			}
			return pList.subList(param.page_start, param.page_start + param.page_counts);
		}
	}

	// public String controllerMethod(@RequestParam(value="myParam[]") String[]
	// myParams){
	// @RequestBody return ResponseEntity.ok()
	// .header("Custom-Header", "foo")
	// .body("Custom header set");
}
class ReqKObjectParam1 {
	public Pagination Pages;
	public KrobjectReq reqObj;
}

class ReqKObjectParam {
	public Pagination Pages;
	public Krobject reqObj;
}

class ResKObjectParam {
	public Pagination Pages;
	public List<Krobject> list;
}