package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface DutyMapper {
    
    public List<Duty> selectByFields(Duty param);
    
    public Long addOne(Duty param);
        
    public int deleteByFields(Duty param);
    
    public int updateByFields(Duty param);

}
