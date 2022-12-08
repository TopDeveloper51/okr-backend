package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface MilestoneMapper {
    
    public List<Milestone> selectByFields(Milestone param);
    
    public Long addOne(Milestone param);
        
    public int deleteByFields(Milestone param);
    
    public int updateByFields(Milestone param);

}
