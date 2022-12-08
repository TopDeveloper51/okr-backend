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
public class IntercomServiceImpl implements IntercomService {

    @Autowired
    private IntercomMapper mMapper;
    
    @Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @Override
    public List<Intercom> selectByFields(Intercom param){
        
    	if(param.ic_receiver!=null && param.ic_parent_type == Constants.tb_user) {
    		// param.ic_sender = jwtTokenUtil.getUserDetailFromTokenPhone().id; 
    	}
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Intercom param){
    	// param.ic_sender = jwtTokenUtil.getUserDetailFromTokenPhone().id;
    	return mMapper.addOne(param);
    }
    
    @Override
    public int deleteByFields(Intercom param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Intercom param){
    	return mMapper.updateByFields(param);
    }
    
}