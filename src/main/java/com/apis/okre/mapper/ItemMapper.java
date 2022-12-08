package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface ItemMapper {
    
    public List<Oitem> selectItemByFields(Oitem tPr);
    
    public Long addItem(Oitem tPr);
        
    public int deleteItemByFields(Oitem tPr);
    
    public int updateItem(Oitem tPr);

}
