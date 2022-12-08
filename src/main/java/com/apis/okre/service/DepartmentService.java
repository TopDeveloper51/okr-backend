package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface DepartmentService {

    public List<Department> selectByFields(Department param);
    
    public Long addOne(Department param);
        
    public int deleteByFields(Department param);
    
    public int updateByFields(Department param);
    
}