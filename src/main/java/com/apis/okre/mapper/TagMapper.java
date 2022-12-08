package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface TagMapper {
    
    public List<Tag> selectByFields(Tag param);
    
    public Long addOne(Tag param);
        
    public int deleteByFields(Tag param);
    
    public int updateByFields(Tag param);

}
