package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface KrobjectMapper {
	
    public List<Krobject> getAllObjectByCriteria(Krobject param);
    public Krobject getOneObjectById(String objectId);
    public Long addObject(KrobjectReq kobj);
    public int deleteByFields(Krobject param);
    public int updateObjectByField(Krobject param);
    
    public List<Krobject> getLibraryMenu();
    public List<Krobject> getLibraryByCriteria(Krobject param);
}