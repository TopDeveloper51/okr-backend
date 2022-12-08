package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface SettingMapper {
    
    public List<Setting> selectByFields(Setting param);
    
    public Long addOne(Setting param);
        
    public int deleteByFields(Setting param);
    
    public int updateByFields(Setting param);

}
