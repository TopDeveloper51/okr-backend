package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper mMapper;
    
    @Override
    public List<Tag> selectByFields(Tag param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Tag param){
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Tag param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Tag param){
    	return mMapper.updateByFields(param);
    }
    
}