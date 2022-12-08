package com.apis.okre.service.impl;

import java.util.Iterator;
import java.util.List;

import org.json.*;
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
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private OperatingService opService;
    
    @Autowired
    private MilestoneService msService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    
    @Override
    public List<Oitem> selectItemByFields(Oitem itemObj) {
    	itemObj.item_operator = jwtTokenUtil.getUserDetailFromTokenPhone();
    	itemObj.item_operator.user_dp_serial = itemObj.item_operator.user_dp_serial+"."; 
    	
    	if (itemObj.item_parent_object!= null) {
            itemObj.item_parent_object = "%," + itemObj.item_parent_object + ",%";
        }
    	
        if (itemObj.item_participant != null) {
            itemObj.item_participant = "%," + itemObj.item_participant + ",%";
        }

        if (itemObj.item_followers != null) {
            itemObj.item_followers = "%," + itemObj.item_followers + ",%";
        }

        if (itemObj.dp_serial != null) {
            itemObj.dp_serial = itemObj.dp_serial + ".%";
        }
        
        return itemMapper.selectItemByFields(itemObj);
    }

    @Override
    public Long addItem(Oitem itemObj) {
    	itemObj.item_creator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
    	Long ret = itemMapper.addItem(itemObj);
    	for(int i=0; i<itemObj.item_milestones.size(); i++) {
    		Milestone newMs= new Milestone();
    		newMs = itemObj.item_milestones.get(i);
    		newMs.ms_id = null;
    		newMs.ms_parent_item = itemObj.item_id;
    		newMs.ms_task = "";
    		for(int k=0; k<newMs.ms_tasks.size(); k++) {
    			Otask newTask = new Otask();
        		newTask = newMs.ms_tasks.get(k);
        		newTask.task_id = null;
        		newTask.task_parent_item = itemObj.item_id;
        		taskService.addOne(newTask);
        		newMs.ms_task +=","+newTask.task_id.toString();
    		}
    		msService.addOne(newMs);
    	}
    	for(int j=0; j<itemObj.item_tasks.size(); j++) {
    		Otask newTask = new Otask();
    		newTask = itemObj.item_tasks.get(j);
    		newTask.task_id = null;
    		newTask.task_parent_item = itemObj.item_id;
    		taskService.addOne(newTask);
    	}
    	
    	if (ret > 0) {	
			//operating
			Operating opParam = new Operating();
			opParam.op_operator = itemObj.item_creator;
			opParam.op_parent = itemObj.item_id;
			opParam.op_parent_type = Constants.tb_item;
			opParam.op_description = String.format(Constants.ADD_RECORD, itemObj.item_name,Constants.NAME_MAP.get("item"));
			opService.addOne(opParam);
		}
        return ret;
    }

    @Override
    public int deleteItemByFields(Oitem itemObj) {
    	if (itemObj.item_participant != null) {
            itemObj.item_participant = "%," + itemObj.item_participant + ",%";
        }

        if (itemObj.item_followers != null) {
            itemObj.item_followers = "%," + itemObj.item_followers + ",%";
        }
        
        if (itemObj.item_parent_object != null) {
            itemObj.item_parent_object = "%," + itemObj.item_parent_object + ",%";
        }
        
        if (itemObj.item_id != null) {
        	Operating opParam = new Operating();
    		opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
    		opParam.op_parent = itemObj.item_id;
    		opParam.op_parent_type = Constants.tb_item;
    		opParam.op_description = String.format(Constants.DEL_RECORD, this.selectItemByFields(itemObj).get(0).item_name, Constants.NAME_MAP.get("item") );
    		opService.addOne(opParam);
        }
		
		
        int ret = itemMapper.deleteItemByFields(itemObj);
        return ret; 
    }

    @Override
    public int updateItem(Oitem itemObj) {
    	int ret = itemMapper.updateItem(itemObj);
    	
    	Operating opParam = new Operating();
		opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
		opParam.op_parent = itemObj.item_id;
		opParam.op_parent_type = Constants.tb_item;
		
    	if (ret > 0) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.valueToTree(itemObj);
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