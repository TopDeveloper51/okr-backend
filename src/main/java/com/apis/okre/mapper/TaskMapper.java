package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface TaskMapper {
    
    public List<Otask> selectByFields(Otask taskObj);
    
    public Long addOne(Otask taskObj);
        
    public int deleteByFields(Otask taskObj);
    
    public int updateByFields(Otask taskObj);

}
