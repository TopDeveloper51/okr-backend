package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface OperatingMapper {
    
    public List<Operating> selectByFields(Operating param);
    
    public Long addOne(Operating param);
        
    public int deleteByFields(Operating param);

}
