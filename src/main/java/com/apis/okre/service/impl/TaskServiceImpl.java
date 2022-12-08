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
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper mMapper;
    
    @Autowired
    private OperatingService opService;
    
    @Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    
    @Override
    public List<Otask> selectByFields(Otask param){
    	param.operator = jwtTokenUtil.getUserDetailFromTokenPhone();
    	param.operator.user_dp_serial = param.operator.user_dp_serial+"."; 
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Otask param){
    	// param.task_creator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
    	Long result = mMapper.addOne(param);
    	if (result != 0L) {	
			//operating
			Operating opParam = new Operating();			
			opParam.op_operator = param.task_creator;
			opParam.op_parent = param.task_id;
			opParam.op_parent_type = Constants.tb_task;
			opParam.op_description = String.format(Constants.ADD_RECORD, param.task_name,Constants.NAME_MAP.get("task"));
			opService.addOne(opParam);
		}
    	return result;
    }
    
    @Override
    public int deleteByFields(Otask param){
    	if(this.selectByFields(param).size()>0) {
    		Operating opParam = new Operating();
    		opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
    		opParam.op_parent = param.task_id;
    		opParam.op_parent_type = Constants.tb_task;
    		opParam.op_description = String.format(Constants.DEL_RECORD, this.selectByFields(param).get(0).task_name, Constants.NAME_MAP.get("task") );
    		opService.addOne(opParam);
    	}		
    	int ret = mMapper.deleteByFields(param);
    	return ret; 
    }
    
    @Override
    public int updateByFields(Otask param){
    	if(param.task_status!=null && param.task_status.contains("1")) {
    		param.task_progress = 100;
    	}
    	int ret = mMapper.updateByFields(param); 
    	
    	Operating opParam = new Operating();
		opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
		opParam.op_parent = param.task_id;
		opParam.op_parent_type = Constants.tb_task;
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