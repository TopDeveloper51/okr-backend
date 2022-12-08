package com.apis.okre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.okre.entity.*;
import com.apis.okre.mapper.*;
import com.apis.okre.service.*;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper mMapper;

	@Autowired
	private ReviewProblemMapper rpMapper;

	@Autowired
	private TaskService taskService;

	@Override
	public List<Review> selectByFields(Review param) {
		return mMapper.selectByFields(param);
	}

	@Override
	public Long addOne(Review param) {
		Long result = mMapper.addOne(param);
		if (result > 0) {
			// add review problems
			for (int i = 0; i < param.re_problems.size(); i++) {
				ReviewProblem rp = param.re_problems.get(i);
				rp.rp_parent = param.re_id;
				Long krres = rpMapper.addOne(rp);
			}

			// add task
			for (int i = 0; i < param.re_tasks.size(); i++) {
				Otask newTask = param.re_tasks.get(i);
				newTask.task_parent_review = param.re_id;
				if (param.re_parent_type == 0) {
					newTask.task_parent_object = param.re_parent;
				}
				if (param.re_parent_type == 1) {
					newTask.task_parent_kr = param.re_parent;
				}
				Long krres = taskService.addOne(newTask);
			}
		}
		return result;
	}

	@Override
	public int deleteByFields(Review param) {
		return mMapper.deleteByFields(param);
	}

	@Override
	public int updateByFields(Review param) {
		int result = mMapper.updateByFields(param);
		for (int i = 0; i < param.re_problems.size(); i++) {
			ReviewProblem rp = param.re_problems.get(i);
			rp.rp_parent = param.re_id;
			if (rp.rp_id == null) {
				Long krres = rpMapper.addOne(rp);
			} else {
				int krres = rpMapper.updateByFields(rp);
			}
		}
		for (int i = 0; i < param.re_tasks.size(); i++) {
			Otask newTask = param.re_tasks.get(i);
			newTask.task_parent_review = param.re_id;
			if (newTask.task_id == null) {
				Long krres = taskService.addOne(newTask);
			}
		}
		return result;
	}

}