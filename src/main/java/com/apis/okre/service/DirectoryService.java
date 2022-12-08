package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface DirectoryService {

    public List<Directory> selectByFields(Directory param);
    
    public Long addOne(Directory param);
        
    public int deleteByFields(Directory param);
    
    public int updateByFields(Directory param);
    
}