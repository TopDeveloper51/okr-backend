package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface WorktimeService {

    public List<Worktime> selectByFields(Worktime param);
    
    public Long addOne(Worktime param);
        
    public int deleteByFields(Worktime param);
    
    public int updateByFields(Worktime param);
    
}