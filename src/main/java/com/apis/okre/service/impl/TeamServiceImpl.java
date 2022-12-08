package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper mMapper;
    
    @Override
    public List<Team> selectByFields(Team param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Team param){
    	Long result =  mMapper.addOne(param);
    	return result;
    }
    
    @Override
    public int deleteByFields(Team param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Team param){
    	return mMapper.updateByFields(param);
    }
    
}