package com.apis.okre.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apis.okre.entity.*;

@Mapper
public interface KanbanMapper {
    
    public List<Kanban> selectByFields(Kanban param);
    
    public Long addOne(Kanban param);
        
    public int deleteByFields(Kanban param);
    
    public int updateByFields(Kanban param);

}
