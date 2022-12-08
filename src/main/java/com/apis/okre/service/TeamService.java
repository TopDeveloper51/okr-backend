package com.apis.okre.service;

import java.util.List;

import com.apis.okre.entity.*;

public interface TeamService {

    public List<Team> selectByFields(Team param);
    
    public Long addOne(Team param);
        
    public int deleteByFields(Team param);
    
    public int updateByFields(Team param);
    
}