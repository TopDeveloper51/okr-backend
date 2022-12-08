package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.Krobject;
import com.apis.okre.entity.Oitem;
import com.apis.okre.entity.Tprogress;

public interface ItemService {

    public List<Oitem> selectItemByFields(Oitem tPr);
    
    public Long addItem(Oitem tPr);
        
    public int deleteItemByFields(Oitem tPr);
    
    public int updateItem(Oitem tPr);
    
}