package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface TagService {

    public List<Tag> selectByFields(Tag param);
    
    public Long addOne(Tag param);
        
    public int deleteByFields(Tag param);
    
    public int updateByFields(Tag param);
    
}