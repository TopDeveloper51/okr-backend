package com.apis.okre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class JwtUserDetailsService implements UserDetailsService {
	// @Autowired
	// private User user;

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
		User param = new User();
		if(userPhone.contains("@")) {
			param.email = userPhone;
		}else {
			param.phone = userPhone;
		}
		
		List<User> userlist = userMapper.selectByFields(param);
		
		return new org.springframework.security.core.userdetails.User(userPhone,
					userlist.get(0).user_password, new ArrayList<>());
		
	}

}