package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.JwtTokenUtil;

@Service
public class WorktimeServiceImpl implements WorktimeService {

    @Autowired
    private WorktimeMapper mMapper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
    @Override
    public List<Worktime> selectByFields(Worktime param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Worktime param){
    	param.wt_creator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Worktime param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Worktime param){
    	return mMapper.updateByFields(param);
    }
    
}