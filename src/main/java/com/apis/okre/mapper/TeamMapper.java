package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface TeamMapper {
    
    public List<Team> selectByFields(Team param);
    
    public Long addOne(Team param);
        
    public int deleteByFields(Team param);
    
    public int updateByFields(Team param);

}
