package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface ReportMapper {
    
    public List<Report> selectByFields(Report param);
    
    public Long addOne(Report param);
        
    public int deleteByFields(Report param);
    
    public int updateByFields(Report param);

}
