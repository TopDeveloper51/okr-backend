package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface ReportService {

    public List<Report> selectByFields(Report param);
    
    public Long addOne(Report param);
        
    public int deleteByFields(Report param);
    
    public int updateByFields(Report param);
    
}