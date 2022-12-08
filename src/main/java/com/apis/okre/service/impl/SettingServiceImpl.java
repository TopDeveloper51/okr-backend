package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingMapper mMapper;
    
    @Override
    public List<Setting> selectByFields(Setting param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Setting param){
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Setting param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Setting param){
    	return mMapper.updateByFields(param);
    }
    
}