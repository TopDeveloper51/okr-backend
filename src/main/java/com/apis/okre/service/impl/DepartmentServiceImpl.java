package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper mMapper;
    
    @Override
    public List<Department> selectByFields(Department param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Department param){
    	return mMapper.addOne(param);
    }
    
    @Override
    public int deleteByFields(Department param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Department param){
    	return mMapper.updateByFields(param);
    }
    
}