package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface OperatingService {

    public List<Operating> selectByFields(Operating param);
    
    public Long addOne(Operating param);
        
    public int deleteByFields(Operating param);
    
    
}