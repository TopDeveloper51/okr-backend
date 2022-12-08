package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface IntercomService {

    public List<Intercom> selectByFields(Intercom param);
    
    public Long addOne(Intercom param);
        
    public int deleteByFields(Intercom param);
    
    public int updateByFields(Intercom param);
    
}