package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface SettingService {

    public List<Setting> selectByFields(Setting param);
    
    public Long addOne(Setting param);
        
    public int deleteByFields(Setting param);
    
    public int updateByFields(Setting param);
    
}