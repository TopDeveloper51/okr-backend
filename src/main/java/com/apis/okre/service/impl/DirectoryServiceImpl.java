package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired
    private DirectoryMapper mMapper;
    
    @Override
    public List<Directory> selectByFields(Directory param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Directory param){
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Directory param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Directory param){
    	return mMapper.updateByFields(param);
    }
    
}