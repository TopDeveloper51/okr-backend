package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface ReviewProblemMapper {
        
    public Long addOne(ReviewProblem param);
        
    public int deleteByFields(ReviewProblem param);
    
    public int updateByFields(ReviewProblem param);

}
