package com.apis.okre.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;
import com.apis.okre.util.Constants;
import com.apis.okre.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper mMapper;

    @Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private OperatingService opService;
    
    @Override
    public List<Report> selectByFields(Report param){
    	return mMapper.selectByFields(param);
    }
    
    @Override
    public Long addOne(Report param){
    	Long result =  mMapper.addOne(param);
    	if (result != 0L) {	
			//operating
			Operating opParam = new Operating();
			opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
			opParam.op_parent = param.rp_id;
			opParam.op_parent_type = Constants.tb_report;
			switch(param.rp_type) {
			case 0:
				opParam.op_description = Constants.ADD_DREPORT;
				break;
			case 1:
				opParam.op_description = Constants.ADD_WREPORT;
				break;
			case 2:
				opParam.op_description = Constants.ADD_MREPORT;
				break;
			}
			
			opService.addOne(opParam);
		}
    	return result;
    }
    
    @Override
    public int deleteByFields(Report param){
    	return mMapper.deleteByFields(param);
    }
    
    @Override
    public int updateByFields(Report param){
    	int ret = mMapper.updateByFields(param);
    	Report tmp = new Report();
    	tmp.rp_id = param.rp_id;
    	tmp = this.selectByFields(tmp).get(0);
    	if (ret != 0) {	
			//operating
			Operating opParam = new Operating();
			opParam.op_operator = jwtTokenUtil.getUserDetailFromTokenPhone().id;
			opParam.op_parent = param.rp_id;
			opParam.op_parent_type = Constants.tb_report;
			switch(tmp.rp_type) {
			case 0:
				opParam.op_description = Constants.UPDATE_DREPORT;
				break;
			case 1:
				opParam.op_description = Constants.UPDATE_WREPORT;
				break;
			case 2:
				opParam.op_description = Constants.UPDATE_MREPORT;
				break;
			}
			opService.addOne(opParam);
		}
    	return ret;
    }
    
}