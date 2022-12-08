package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface WorktimeMapper {
    
    public List<Worktime> selectByFields(Worktime param);
    
    public Long addOne(Worktime param);
        
    public int deleteByFields(Worktime param);
    
    public int updateByFields(Worktime param);

}
