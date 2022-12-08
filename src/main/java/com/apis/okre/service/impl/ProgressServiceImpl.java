package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.Constants;
import com.apis.okre.util.JwtTokenUtil;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressMapper mMapper;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private KrobjectService objectService;
    
    @Autowired
    private KrService krService;
    
    @Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @Override
    public List<Tprogress> selectByFields(Tprogress param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Tprogress param){
    	param.pr_creator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
    	Integer percent = 0;
    	if(param.pr_percent!=null) {
    		switch(param.pr_parent_type) {
	    		case Constants.tb_item:
	    			Oitem itemObj = new Oitem();
	    			itemObj.item_id = param.pr_parent;
	    			itemObj.item_progress = param.pr_percent;
	    			itemService.updateItem(itemObj);
	    			break;
	    		case Constants.tb_object:
	    			Krobject resObj = objectService.getOneObjectById(param.pr_parent.toString());
	    			param.pr_up_percent = param.pr_percent - resObj.ob_progress;
	    			
	    			Krobject newObj = new Krobject();
	    			newObj.ob_id = param.pr_parent;
	    			newObj.ob_progress = param.pr_percent;
	    			objectService.updateObjectByField(newObj);
	    			break;
	    		case Constants.tb_kr:
	    			Kresult krObj = new Kresult();
	    			krObj.kr_id = param.pr_parent;
	    			krObj.kr_completion = param.pr_percent;
	    			krService.updateKrByField(krObj);
	    			break;
    		}
    	}
    	
    	if(param.pr_percent==null) {
    		switch(param.pr_parent_type) {
	    		case Constants.tb_kr:
	    			Kresult krObj = new Kresult();
	    			krObj.kr_id = param.pr_parent;
	    			param.pr_percent = krService.getDetailedKr(krObj).get(0).kr_completion;
	    			break;
    		}
    	}
    	
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Tprogress param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Tprogress param){
    	int ret = mMapper.updateByFields(param);
    	return ret;
    	
    }
    
}