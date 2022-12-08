package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface KanbanService {

    public List<Kanban> selectByFields(Kanban param);
    
    public Long addOne(Kanban param);
        
    public int deleteByFields(Kanban param);
    
    public int updateByFields(Kanban param);
    
}