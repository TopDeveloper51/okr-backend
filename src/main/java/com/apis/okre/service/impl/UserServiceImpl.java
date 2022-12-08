package com.apis.okre.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.UserService;
import com.apis.okre.util.Constants;
import com.apis.okre.util.JwtTokenUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mMapper;

	@Autowired
	private DepartmentMapper DpMapper;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${user.default.password}")
	private String pwd;

	@Override
	public List<User> selectByFields(User param) {
		
		User tmp = jwtTokenUtil.getUserDetailFromTokenPhone();
		if(param.id ==null) {
			param.operator = tmp;
			param.user_company_id = tmp.user_company_id;
		}
		if(param.user_dp_serial != null) {
			param.user_dp_serial = param.user_dp_serial +".%";
		}
		return mMapper.selectByFields(param);
	}

	@Override
	public Long addOne(User param) {
		param.user_password = bcryptEncoder.encode(param.user_password);

		User doublePhoneUser = new User();
		doublePhoneUser.phone = param.phone;
		List<User> user1 = mMapper.selectByFields(doublePhoneUser);
		if (user1.size() > 0) {
			return 2L;
		}
		User tmp = jwtTokenUtil.getUserDetailFromTokenPhone();
		Department dp = new Department();
		if(param.user_company_id==null) {
			dp.departmentOaName = param.user_company_name;
			dp.pid = 0L;
			dp.dp_serial_id = "";
			DpMapper.addOne(dp);
			param.user_company_id = dp.id;
		}
		param.user_dot_superior = 0L;
		param.user_superior = 0L;
		param.user_role = "creator";
		param.user_post_address = param.employeeName + ":" + UUID.randomUUID().toString() + "@virtual.account";
		return mMapper.addOne(param);
	}

	@Override
	public int deleteByFields(User param) {
		if(jwtTokenUtil.getUserDetailFromTokenPhone().user_role.contains("creator")) {
			return mMapper.deleteByFields(param);
		}else {
			return 0;
		}		
	}

	@Override
	public int updateByFields(User param) {
		if(jwtTokenUtil.getUserDetailFromTokenPhone().user_role.contains("creator")) {
			if (param.user_password != null) {
				param.user_password = bcryptEncoder.encode(pwd);
			}
			return mMapper.updateByFields(param);
		}else {
//			if(param.id==jwtTokenUtil.getUserDetailFromTokenPhone().id) {
				if (param.user_password != null) {
					param.user_password = bcryptEncoder.encode(param.user_password);
				}
				return mMapper.updateByFields(param);
//			}else {
//				return 0;
//			}
		}
	}

}