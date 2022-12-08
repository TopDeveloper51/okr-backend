package com.apis.okre.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.Constants;
import com.apis.okre.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    private MilestoneMapper mMapper;
    
    @Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private OperatingService opService;
    
    
    @Override
    public List<Milestone> selectByFields(Milestone param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Milestone param){
    	Long result =  mMapper.addOne(param);
    	if (result != 0L) {	
			//operating
			Operating opParam = new Operating();
			opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
			opParam.op_parent = param.ms_id;
			opParam.op_parent_type = Constants.tb_milestone;
			opParam.op_description = String.format(Constants.ADD_RECORD, param.ms_name,Constants.NAME_MAP.get("milestone"));
			opService.addOne(opParam);
		}
    	return result;
    }
    
    @Override
    public int deleteByFields(Milestone param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Milestone param){
    	int ret = mMapper.updateByFields(param);
    	
    	Operating opParam = new Operating();
		opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
		opParam.op_parent = param.ms_id;
		opParam.op_parent_type = Constants.tb_milestone;
    	if (ret != 0) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.valueToTree(param);
			Iterator<String> fieldNames = node.fieldNames();

			while(fieldNames.hasNext()) {
			    String fieldName = fieldNames.next();
			    JsonNode value = node.get(fieldName);
			    if(value != null && !value.isNull() && !value.isArray() && Constants.NAME_MAP.get(fieldName)!=null && Constants.NAME_MAP.get(fieldName)!="") {
			    	opParam.op_description = String.format(Constants.UPDATE_RECORD,Constants.NAME_MAP.get(fieldName), value.toString());
					opService.addOne(opParam);
			    }
			}
		}
    	
    	return ret;
    }
    
}