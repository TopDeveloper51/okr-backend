package com.apis.okre.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.apis.okre.entity.User;

public interface UserService {
    public List<User> selectByFields(User param);
    public Long addOne(User param);
    public int deleteByFields(User param);
    public int updateByFields(User param);
    
}