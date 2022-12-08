package com.apis.okre.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KrServiceImpl implements KrService {

	@Autowired
	private KresultMapper krMapper;

	@Autowired
	private KrobjectServiceImpl obServiceImpl;

	@Autowired
	private OperatingMapper opMapper;
	
	@Autowired
	private KrobjectMapper obMapper;
	
	@Autowired
	private OperatingService opService;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtService;

	@Override
	public List<Kresult> getDetailedKr(Kresult kr) {
		return krMapper.getDetailedKr(kr);
	}

	@Override
	public Long addKresult(Kresult kr) {
		
		kr.kr_creator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
		Long ret = krMapper.addKresult(kr);
		if (ret != 0L) {
			Operating opParam = new Operating();
			opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
			opParam.op_parent = kr.kr_id;
			opParam.op_parent_type = Constants.tb_kr;
			opParam.op_description = String.format(Constants.ADD_KRESULT_RECORD, kr.kr_name);
			opMapper.addOne(opParam);
			
			//auto progress
			Krobject ob1 = new Krobject();
			ob1.ob_id = kr.kr_parent_object;
			ob1.ob_progress = -1;
			obServiceImpl.updateObjectByField(ob1);
		}
						
		return ret;
	}

	@Override
	public int deleteKresultById(Long id) {
		return krMapper.deleteKresultById(id);
	}

	@Override
	public int deleteKresultByParentObject(Kresult kr) {
		// auto progress
		List<Kresult> kr1 =new ArrayList<Kresult>();
		if(kr.kr_id != null) {
			Kresult krParam = new Kresult();
			krParam.kr_id = kr.kr_id;
			kr1 = krMapper.getDetailedKr(krParam);
		}
		int ret = krMapper.deleteKresultByParentObject(kr);
		
		if(ret>0) {
			if (kr1.size()>0) {
				Krobject ob1 = new Krobject();
				ob1.ob_id = kr1.get(0).kr_parent_object;
				ob1.ob_progress = -1;
				obServiceImpl.updateObjectByField(ob1);
			}
		}
		
		return ret;
	}

	@Override
	public int updateKrByField(Kresult krObj) {
		int ret = krMapper.updateKrByField(krObj);
		
		Kresult krParam = new Kresult();
		krParam.kr_id = krObj.kr_id;
		List<Kresult> kr1 = krMapper.getDetailedKr(krParam);
		
		// auto progress
		if (ret != 0 && krObj.kr_completion != null) {
			Krobject ob1 = new Krobject();
			ob1.ob_id = kr1.get(0).kr_parent_object;
			ob1.ob_progress = -1;
			obServiceImpl.updateObjectByField(ob1);
		}
		
		// calculate parent object's score
		if (ret != 0 && krObj.kr_score != null) {
			Krobject ob1 = new Krobject();
			ob1.ob_id = kr1.get(0).kr_parent_object;
			ob1.ob_progress = -3;
			obMapper.updateObjectByField(ob1);
		}

		Operating opParam = new Operating();
		opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
		opParam.op_parent = krObj.kr_id;
		opParam.op_parent_type = Constants.tb_kr;
		// update operating
		if (ret != 0) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.valueToTree(krObj);
			Iterator<String> fieldNames = node.fieldNames();

			while(fieldNames.hasNext()) {
			    String fieldName = fieldNames.next();
			    JsonNode value = node.get(fieldName);
			    if(value != null && !value.isNull() && !value.isArray() && Constants.NAME_MAP.get(fieldName)!=null && Constants.NAME_MAP.get(fieldName)!="") {
			    	opParam.op_description = String.format(Constants.UPDATE_OBJECT_RECORD,Constants.NAME_MAP.get(fieldName), value.toString());
					opService.addOne(opParam);
			    }
			}
		}

		return ret;
	}
}