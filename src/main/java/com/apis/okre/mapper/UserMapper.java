package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.apis.okre.entity.User;

@Mapper
public interface UserMapper {
	
	public List<User> selectByFields(User param);
	
    public Long addOne(User param);
        
    public int deleteByFields(User param);
    
    public int updateByFields(User param);

}