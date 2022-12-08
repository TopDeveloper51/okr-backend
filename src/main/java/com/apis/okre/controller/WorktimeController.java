package com.apis.okre.controller;

import java.util.List;

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
import com.apis.okre.service.*;
import com.apis.okre.util.Constants;
import com.apis.okre.util.Utils;

/**
 * table: tb_worktime
 * @author alex
 *
 */
@RestController
@SpringBootApplication
@CrossOrigin
@RequestMapping("/wt")
public class WorktimeController {

	@Autowired
	private WorktimeService mService;
	
	@RequestMapping(value = "/getByFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> selectByFields(@RequestBody Worktime param) {
		try {
			List<Worktime> ret = mService.selectByFields(param);
			return new ResponseEntity<String>(Utils.getResponseObject(Constants.SEARCH_SUCCESSFULLY, ret).toString(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/addOne", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addOne(@RequestBody Worktime param) {
		try {
			Long ret = mService.addOne(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				Worktime newObj = new Worktime();
				newObj.wt_id = param.wt_id;
				List<Worktime> retObj = mService.selectByFields(newObj);
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.INSERT_SUCCESSFULLY, retObj.get(0)).toString(),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/deleteByFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteByFields(@RequestBody Worktime param) {
		try {
			int ret = mService.deleteByFields(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.DELETE_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.DELETE_SUCCESSFULLY, ret).toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/updateByFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateByFields(@RequestBody Worktime param) {
		try {
			int ret = mService.updateByFields(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.UPDATE_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.UPDATE_SUCCESSFULLY, ret).toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
}
