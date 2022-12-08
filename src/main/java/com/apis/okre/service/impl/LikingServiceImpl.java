package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class LikingServiceImpl implements LikingService {

    @Autowired
    private LikingMapper mMapper;
    
    @Override
    public List<Liking> selectByFields(Liking param){
    	List<Liking> ret = mMapper.selectByFields(param);
    	if(ret.size()==0 && param.like_parent!=null && param.like_parent_type!=null) {

    		this.addOne(param);
    		ret = mMapper.selectByFields(param);
    	}
    	return ret;
    }
    
    @Override
    public Long addOne(Liking param){
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Liking param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Liking param){
    	return mMapper.updateByFields(param);
    }
    
}