package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface LikingMapper {
    
    public List<Liking> selectByFields(Liking param);
    
    public Long addOne(Liking param);
        
    public int deleteByFields(Liking param);
    
    public int updateByFields(Liking param);

}
