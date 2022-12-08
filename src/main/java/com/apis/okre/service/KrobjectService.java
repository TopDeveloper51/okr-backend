package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.Krobject;
import com.apis.okre.entity.KrobjectReq;

public interface KrobjectService {

    public List<Krobject> getAllObjectByCriteria(Krobject param);
    public Long addObjectKr (KrobjectReq param);
    public Krobject getOneObjectById(String objectId);
    public int updateObjectByField (Krobject param);
    public int deleteByFields(Krobject param);
}