package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface TaskService {

    public List<Otask> selectByFields(Otask param);
    
    public Long addOne(Otask param);
        
    public int deleteByFields(Otask param);
    
    public int updateByFields(Otask param);
    
}