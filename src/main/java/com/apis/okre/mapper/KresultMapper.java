package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.apis.okre.entity.*;

@Mapper
public interface KresultMapper {
	
    public List<Kresult> getDetailedKr(Kresult kr);
    
    public Long addKresult(Kresult kr);
    
    public int deleteKresultById(Long id);
    
    public int deleteKresultByParentObject(Kresult kr);
    
    public int updateKrByField(Kresult kr);
}
