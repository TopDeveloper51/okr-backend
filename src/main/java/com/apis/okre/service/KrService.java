package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface KrService {
	public List<Kresult> getDetailedKr(Kresult kr);
    
    public Long addKresult(Kresult kr);
    
    public int deleteKresultById(Long id);
    
    public int deleteKresultByParentObject(Kresult kr);
    
    public int updateKrByField (Kresult krObj);
}