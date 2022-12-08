package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface DirectoryMapper {
    
    public List<Directory> selectByFields(Directory param);
    
    public Long addOne(Directory param);
        
    public int deleteByFields(Directory param);
    
    public int updateByFields(Directory param);

}
