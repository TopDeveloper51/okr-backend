package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface DepartmentMapper {
    
    public List<Department> selectByFields(Department taskObj);
    
    public Long addOne(Department taskObj);
        
    public int deleteByFields(Department taskObj);
    
    public int updateByFields(Department taskObj);

}
