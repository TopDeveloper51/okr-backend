package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface ReviewMapper {
    
    public List<Review> selectByFields(Review param);
    
    public Long addOne(Review param);
        
    public int deleteByFields(Review param);
    
    public int updateByFields(Review param);

}
