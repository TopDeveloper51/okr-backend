package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface IntercomMapper {
    
    public List<Intercom> selectByFields(Intercom taskObj);
    
    public Long addOne(Intercom taskObj);
        
    public int deleteByFields(Intercom taskObj);
    
    public int updateByFields(Intercom taskObj);

}
