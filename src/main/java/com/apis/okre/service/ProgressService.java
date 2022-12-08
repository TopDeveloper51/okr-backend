package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.Tprogress;

public interface ProgressService {

	public List<Tprogress> selectByFields(Tprogress param);
    
    public Long addOne(Tprogress param);
        
    public int deleteByFields(Tprogress param);
    
    public int updateByFields(Tprogress param);
}