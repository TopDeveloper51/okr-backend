package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface VisitMapper {
    
    public List<Visit> selectByFields(Visit param);
    
    public Long addOne(Visit param);
        
    public int deleteByFields(Visit param);
    
    public int updateByFields(Visit param);

}
