package com.apis.okre.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.Constants;
import com.apis.okre.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KrobjectServiceImpl implements KrobjectService {

	@Autowired
	private KrobjectMapper objectMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private KresultMapper krMapper;

	@Autowired
	private KrService krService;

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private OperatingMapper opMapper;
	
	@Autowired
	private OperatingService opService;
	
	@Autowired
	private VisitMapper vsMapper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public List<Krobject> getAllObjectByCriteria(Krobject param) {
		if (param.ob_attention != null) {
			param.ob_attention = "%," + param.ob_attention + ",%";
		}
		if (param.ob_participant != null) {
			param.ob_participant = "%," + param.ob_participant + ",%";
		}
		if (param.ob_operator != null) {
			if (param.ob_operator.contains("dp")) {
				param.ob_operator = param.ob_operator + ".%";
			}
		}
		
		param.operator = jwtTokenUtil.getUserDetailFromTokenPhone();
		
		if (param.ob_owner != null && param.ob_owner!=param.operator.id) {
			Visit newVs = new Visit();
			newVs.vs_visitor = param.operator.id;
			newVs.vs_target = param.ob_owner;
			newVs.vs_target_type = Constants.tb_user;
			if(vsMapper.selectByFields(newVs).size()>0) {
				vsMapper.updateByFields(newVs);
			}else {
				vsMapper.addOne(newVs);
			}
		}
		
		param.operator.user_dp_serial = param.operator.user_dp_serial+"."; 
		param.ob_company_id = param.operator.user_company_id;
		
		List<Krobject> list = objectMapper.getAllObjectByCriteria(param);
		return list;
	}

	@Override
	public Krobject getOneObjectById(String objectId) {

		Krobject kobj = objectMapper.getOneObjectById(objectId);
		return kobj;
	}

	@Override
	public int deleteByFields(Krobject param) {
		User authUser = jwtTokenUtil.getUserDetailFromTokenPhone();
		Long operator_id = authUser.id;
		
		
		//operating
		Krobject delObj = new Krobject();
		delObj.ob_id = param.ob_id;
		delObj = objectMapper.getOneObjectById(delObj.ob_id.toString()) ;
		if( delObj !=null) {
			//if(!authUser.user_role.contains("creator") && authUser.id!=delObj.ob_owner) {
			if(!authUser.user_role.contains("creator") && !authUser.user_role.contains("editor")) {
				return -1;
			}
		}
		
		
		int ret = objectMapper.deleteByFields(param);
		
//		Krobject delObj = new Krobject();
//		delObj.ob_id = param.ob_id;
//		delObj.ob_mstatus = 1;
//		int ret = objectMapper.updateObjectByField(delObj);
	
		// delete children kresultes of the object
		Kresult kr = new Kresult();
		kr.kr_parent_object = param.ob_id;
		krService.deleteKresultByParentObject(kr);

		// update the object has parent object for this object to NULL
		Krobject parentObj = new Krobject();
		parentObj.ob_parent_object = param.ob_id;
		objectMapper.updateObjectByField(parentObj);

		if (param.ob_status != null) {
			// delete related items for this object
			Oitem delItem = new Oitem();
			delItem.item_parent_object = param.ob_id.toString();
			
			// delete related items for this object
			Otask delTask = new Otask();
			delTask.task_parent_object = param.ob_id;
					
			switch (param.ob_status) {
				case 0:
				
					break;
				case 1:
					itemService.deleteItemByFields(delItem);
					taskService.deleteByFields(delTask);
					break;
				case 2:
					//item
					delItem.item_owner = operator_id;
					itemService.deleteItemByFields(delItem);
					
					delItem.item_owner = null;
					delItem.item_participant = operator_id.toString();
					itemService.deleteItemByFields(delItem);

					delItem.item_participant = null;
					delItem.item_followers = operator_id.toString();
					itemService.deleteItemByFields(delItem);
					
					//task
					delTask.task_parent_object = param.ob_id;
					taskService.deleteByFields(delTask);

					// delTask.task_owner = operator_id;
					// taskService.deleteByFields(delTask);
								
					// delTask.task_owner = null;
					// delTask.task_collaborator = operator_id.toString();
					// taskService.deleteByFields(delTask);
					break;
			}
		}
		return ret;
	}
	
	public List<Long> getChildObjectId(Krobject recur){
		List<Long> lists = new ArrayList<Long>();
		if(recur.ob_child_objects.size()>0) {
			for (Krobject item:recur.ob_child_objects) {
				lists.addAll( getChildObjectId(item) );
			}
		}else{
			lists.add(recur.ob_id);
		}
		return lists;
	}

	@Override
	public int updateObjectByField(Krobject param) {
		//permission
		User authUser = jwtTokenUtil.getUserDetailFromTokenPhone();
		if(param.ob_id != null) {
			Krobject updObj = new Krobject();
			updObj.ob_id = param.ob_id;
			updObj = objectMapper.getOneObjectById(updObj.ob_id.toString()) ;
			if( updObj !=null) {
				if(!authUser.user_role.contains("creator") && authUser.id!=updObj.ob_owner) {
					return -3;
				}
			}
		}
		
		
		if(param.ob_id != null && param.ob_parent_object !=null) {
			Krobject tmp = new Krobject();
			tmp.ob_id = param.ob_id;
			List<Long> lists = getChildObjectId(this.getAllObjectByCriteria(tmp).get(0));
			if(lists.contains(param.ob_parent_object)) {
				return -1;
			}
		}

		int ret = objectMapper.updateObjectByField(param);
		
		//update progress by setting parent object.
		if (ret != 0 && param.ob_parent_object != null) {
			Krobject ob1 = new Krobject();
			ob1.ob_id = param.ob_parent_object;
			ob1.ob_progress = -2;
			updateObjectByField(ob1);
		}
				
		// auto progress
		if (ret != 0 && param.ob_progress != null) {
			Krobject obj = objectMapper.getOneObjectById(param.ob_id.toString());
			Krobject ob1 = new Krobject();
			if (obj.ob_parent_object != null) {
				ob1.ob_id = obj.ob_parent_object;
				ob1.ob_progress = -2;
				updateObjectByField(ob1);
			}
		}

		Operating opParam = new Operating();
		opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
		opParam.op_parent = param.ob_id;
		opParam.op_parent_type = Constants.tb_object;
		// update operating
		if (ret != 0 && ((param.ob_progress != null && param.ob_progress>=0) || param.ob_progress ==null) ) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.valueToTree(param);
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

	@Override
	public Long addObjectKr(KrobjectReq param) {
			
		param.ob_creator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
		param.ob_company_id = jwtTokenUtil.getUserDetailFromTokenPhone().user_company_id;
		
		Long result = objectMapper.addObject(param);
		
		if (result != 0L) {
			
			//operating
			Operating opParam = new Operating();
			opParam.op_operator = param.ob_creator;
			opParam.op_parent = param.ob_id;
			opParam.op_parent_type = Constants.tb_object;
			opParam.op_description = String.format(Constants.ADD_OBJECT_RECORD, param.ob_name);
			opService.addOne(opParam);
						
			for (int i = 0; i < param.ob_results.size(); i++) {
				Kresult kr = param.ob_results.get(i);
				kr.kr_parent_object = param.ob_id;
				kr.kr_start_date = param.ob_start_date;
				kr.kr_end_date = param.ob_end_date;
				kr.kr_creator = param.ob_creator;
				result *= krService.addKresult(kr);
				
				if(kr.kr_tasks!=null && kr.kr_tasks.size()>0)
				for (int j = 0; j < kr.kr_tasks.size(); j++) {
					Otask newTask = kr.kr_tasks.get(j);
					newTask.task_parent_kr = kr.kr_id;
					newTask.task_parent_object = param.ob_id;
					newTask.task_creator = param.ob_creator;
					result *= taskService.addOne(newTask);					
				}
			}
		}
		
		return result;
	}
}