package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class OperatingServiceImpl implements OperatingService {

    @Autowired
    private OperatingMapper mMapper;
    
    @Override
    public List<Operating> selectByFields(Operating param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Operating param){
    	return mMapper.addOne(param);
    }
    
    @Override
    public int deleteByFields(Operating param){
    	return mMapper.deleteByFields(param);
    }
    
}