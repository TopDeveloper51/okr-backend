package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface ProgressMapper {
    
	public List<Tprogress> selectByFields(Tprogress param);
    
    public Long addOne(Tprogress param);
        
    public int deleteByFields(Tprogress param);
    
    public int updateByFields(Tprogress param);

}
