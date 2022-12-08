package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface FileMapper {
    
    public List<TFile> selectByFields(TFile param);
    
    public Long addOne(TFile param);
        
    public int deleteByFields(TFile param);
    
    public int updateByFields(TFile param);

}
