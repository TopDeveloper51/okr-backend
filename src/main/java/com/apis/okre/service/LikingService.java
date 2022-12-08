package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface LikingService {

    public List<Liking> selectByFields(Liking param);
    
    public Long addOne(Liking param);
        
    public int deleteByFields(Liking param);
    
    public int updateByFields(Liking param);
    
}