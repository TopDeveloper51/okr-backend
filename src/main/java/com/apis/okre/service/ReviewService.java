package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface ReviewService {

    public List<Review> selectByFields(Review param);
    
    public Long addOne(Review param);
        
    public int deleteByFields(Review param);
    
    public int updateByFields(Review param);
    
}