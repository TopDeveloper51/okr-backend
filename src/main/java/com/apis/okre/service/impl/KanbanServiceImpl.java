package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class KanbanServiceImpl implements KanbanService {

    @Autowired
    private KanbanMapper mMapper;
    
    @Override
    public List<Kanban> selectByFields(Kanban param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Kanban param){
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Kanban param){
    	List<Kanban> pObj = mMapper.selectByFields(param);
    	if(pObj.size()>0) {
    		Kanban shiftObj = new Kanban();
        	shiftObj.kb_order = pObj.get(0).kb_order;
        	shiftObj.kb_parent_item = pObj.get(0).kb_parent_item;
        	mMapper.updateByFields(shiftObj);
    	}    	
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Kanban param){
    	if(param.kb_id!=null && param.kb_order!=null) {
    		Kanban orderObj = new Kanban();
    		orderObj.kb_id = param.kb_id;
    		List<Kanban> pObj = mMapper.selectByFields(orderObj);
    		if(pObj.size()>0) {
        		param.kb_old_order = pObj.get(0).kb_order;  
        		param.kb_parent_item = pObj.get(0).kb_parent_item;
    		}
    	}
    	
    	return mMapper.updateByFields(param);
    }
    
}