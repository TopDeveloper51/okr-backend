package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface MilestoneService {

    public List<Milestone> selectByFields(Milestone param);
    
    public Long addOne(Milestone param);
        
    public int deleteByFields(Milestone param);
    
    public int updateByFields(Milestone param);
    
}