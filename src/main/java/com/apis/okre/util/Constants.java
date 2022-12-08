package com.apis.okre.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class Constants {
	public static final String WRONG_PASSWORD = "密码错误"; // HttpStatus.NON_AUTHORITATIVE_INFORMATION 203
	public static final String NOT_FOUND_PHONE = "用用户电话找不到用户"; // HttpStatus.NOT_FOUND 404
	public static final String NOT_FOUND_EMAIL = "用用户email找不到用户"; // HttpStatus.NOT_FOUND 404
	public static final String PHONE_DUPLICATED = "电话号码重复"; // HttpStatus.CONFLICT 409
	public static final String NOT_FOUND = "未找到"; // HttpStatus.NOT_FOUND 404
	public static final String ADD_USER_SUCCESSFULLY = "添加用户成功";
	public static final String BAD_REQUEST = "错误的请求"; // HttpStatus.BAD_REQUEST 400
	public static final String UPDATE_FAILED = "更新失败"; // HttpStatus.BAD_REQUEST 400
	public static final String DELETE_FAILED = "删除失败"; // HttpStatus.BAD_REQUEST 400
	public static final String INSERT_FAILED = "插入失败"; // HttpStatus.BAD_REQUEST 400
	public static final String UPDATE_SUCCESSFULLY = "更新成功";
	public static final String INSERT_SUCCESSFULLY = "插入成功";
	public static final String DELETE_SUCCESSFULLY = "删除成功";
	public static final String SEARCH_SUCCESSFULLY = "搜索成功";
	public static final String VALIDATION_ERROR= "验证错误";
	public static final String HEALTH_COMMENT = "API 运行良好";
	public static final String DB_HEALTH_COMMENT = "数据库连接不起作用";
	public static final String UPDATE_PARENT_OBJECT_FAILED = "您不能将此目标设置为父目标。";
	public static final String PERMISSION_FAILED = "您无权这样做。"; // HttpStatus.BAD_REQUEST 400

	public static final int tb_department = 5;
	public static final int tb_intercom = 6;
	public static final int tb_item = 3;
	public static final int tb_kanban = 12;
	public static final int tb_kr = 1;
	public static final int tb_milestone = 13;
	public static final int tb_object = 0;
	public static final int tb_operating = 7;
	public static final int tb_progress = 8;
	public static final int tb_report = 4;
	public static final int tb_review = 9;
	public static final int tb_review_problem = 11;
	public static final int tb_tag = 14;
	public static final int tb_task = 2;
	public static final int tb_unit = 15;
	public static final int tb_user = 10;
	public static final int tb_task_intercom = 16;
	public static final int tb_item_intercom = 17;
	public static final int tb_e_report = 18;
	public static final int tb_share_ta = 19;
	public static final int tb_work_summary = 20;
	public static final int tb_dongtai = 21;
	
	public static Map<String, String> 	NAME_MAP;
	static {
		NAME_MAP = new HashMap<>();
		
		NAME_MAP.put("krobject", "目标");
		NAME_MAP.put("ob_name", "目标的名称");
		NAME_MAP.put("ob_owner", "");
		NAME_MAP.put("ob_creator", "");
		NAME_MAP.put("ob_cycle", "目标的周期");
		NAME_MAP.put("ob_start_date", "目标的开始日");
		NAME_MAP.put("ob_end_date", "目标的终止日");
		NAME_MAP.put("ob_company_id", "");
		NAME_MAP.put("ob_parent_object", "");
		NAME_MAP.put("ob_status", "");
		NAME_MAP.put("ob_progress", "目标完成度");
		NAME_MAP.put("ob_auto_progress", "");
		NAME_MAP.put("ob_visible_type", "");
		NAME_MAP.put("ob_visible_range", "");
		NAME_MAP.put("ob_participant", "");
		NAME_MAP.put("ob_attention", "");
		NAME_MAP.put("ob_score", "目标评分");
		NAME_MAP.put("ob_mstatus", "");
		
		NAME_MAP.put("kr", "关键结果");
		NAME_MAP.put("kr_name", "关键成果的名称");
		NAME_MAP.put("kr_owner", "");
		NAME_MAP.put("kr_creator", "");
		NAME_MAP.put("kr_parent_object", "");
		NAME_MAP.put("kr_completion", "关键成果的进度");
		NAME_MAP.put("kr_confidence", "信心");
		NAME_MAP.put("kr_score", "评分");
		NAME_MAP.put("kr_order", "");
		NAME_MAP.put("kr_score_description", "");
		NAME_MAP.put("kr_ta", "");
		NAME_MAP.put("kr_rate", "");
		NAME_MAP.put("kr_start_date", "");
		NAME_MAP.put("kr_end_date", "");
		
		NAME_MAP.put("task", "任务");
		NAME_MAP.put("task_name", "任务的名称");
		NAME_MAP.put("task_status", "");
		NAME_MAP.put("task_start_date", "起时间");
		NAME_MAP.put("task_end_date", "止时间");
		NAME_MAP.put("task_complete_date", "");
		NAME_MAP.put("task_priority", "任务的优先");
		NAME_MAP.put("task_description", "任务描述与备注");
		NAME_MAP.put("task_target_price", "任务的目标值");
		NAME_MAP.put("task_unit", "");
		NAME_MAP.put("task_real_price", "任务的实际值");
		NAME_MAP.put("task_feedback_time", "反馈时间");
		NAME_MAP.put("task_est_worktime", "");
		NAME_MAP.put("task_creator", "");
		NAME_MAP.put("task_owner", "");
		NAME_MAP.put("task_approver", "");
		NAME_MAP.put("task_collaborator", "");
		NAME_MAP.put("task_parent_object", "");
		NAME_MAP.put("task_parent_kr", "");
		NAME_MAP.put("task_parent_item", "");
		NAME_MAP.put("task_parent_review", "");
		NAME_MAP.put("task_parent_task", "");
		NAME_MAP.put("task_refer", "");
		NAME_MAP.put("task_visible_type", "");
		NAME_MAP.put("task_visible_range", "");
		NAME_MAP.put("task_uploaded_files", "");
		NAME_MAP.put("task_order", "");
		NAME_MAP.put("task_vice_leader", "");
		NAME_MAP.put("task_progress", "任务的进度");
		
		NAME_MAP.put("item", "项目");
		NAME_MAP.put("item_name", "项目的名称");
		NAME_MAP.put("item_progress", "项目的进度");
		NAME_MAP.put("item_start_date", "起时间");
		NAME_MAP.put("item_end_date", "止时间");
		NAME_MAP.put("item_owner", "");
		NAME_MAP.put("item_creator", "");
		NAME_MAP.put("item_parent_object", "");
		NAME_MAP.put("item_parent_kr", "");
		NAME_MAP.put("item_participant", "");
		NAME_MAP.put("item_followers", "");
		NAME_MAP.put("item_approver", "");
		NAME_MAP.put("item_description", "");
		NAME_MAP.put("item_visible_range", "");
		NAME_MAP.put("item_company_id", "");
		NAME_MAP.put("item_tag", "");
		NAME_MAP.put("item_uploaded_file", "");
		NAME_MAP.put("item_status", "");
		NAME_MAP.put("item_mstatus", "");
		
		NAME_MAP.put("milestone", "里程碑");
		NAME_MAP.put("ms_name", "里程碑的名称");
		NAME_MAP.put("ms_description", "里程碑的描述");
		NAME_MAP.put("ms_progress", "里程碑的进度");
		NAME_MAP.put("ms_start_date", "起时间");
		NAME_MAP.put("ms_end_date", "止时间");
		NAME_MAP.put("ms_owner", "");
		NAME_MAP.put("ms_parent_item", "");
		NAME_MAP.put("ms_task", "");
		
		NAME_MAP.put("kanban", "看板");
		NAME_MAP.put("kb_name", "看板的名称");
		NAME_MAP.put("kb_order", "");
		NAME_MAP.put("kb_parent_item", "");
		NAME_MAP.put("kb_task", "");
		
		NAME_MAP.put("report", "日报");
		NAME_MAP.put("rp_date", "日报日期");
		NAME_MAP.put("rp_type", "");
		NAME_MAP.put("rp_abstract", "总结与障碍");
		NAME_MAP.put("rp_todo", "下一步工作安排");
		NAME_MAP.put("rp_attatch_files", "");
		NAME_MAP.put("rp_visible_range", "");
		NAME_MAP.put("rp_ta_contacts", "");
		NAME_MAP.put("rp_send_check", "");
		NAME_MAP.put("rp_publish_type", "");
		NAME_MAP.put("rp_creator", "");
		NAME_MAP.put("rp_tag", "");
		NAME_MAP.put("rp_progress_tasks", "");
		NAME_MAP.put("rp_expired_tasks", "");
		NAME_MAP.put("rp_completed_tasks", "");

	}
	
	// --------------- Kresult Operating -----------------
	
	public static final String UPDATE_KRESULT_RECORD = "更新了%s为 \"%s\" ";
	
	public static final String ADD_KRESULT_RECORD = "创建了名为 \"%s\" 的关键结果";
	
	public static final String DEL_KRESULT_RECORD = "删除了名为 %s 的关键结果";
	
	// -------------Object Operating-------------------
	
	public static final String UPDATE_OBJECT_RECORD = "更新了%s为 %s ";
	
	public static final String ADD_OBJECT_RECORD = "创建了名为 \"%s\" 的目标";
	
	public static final String DEL_OBJECT_RECORD = "删除了名为 %s 的目标";
	
	// -------------Operating-------------------
	
		public static final String UPDATE_RECORD = "更新了%s为 %s ";
		
		public static final String ADD_RECORD = "创建了名为 \"%s\" 的%s";
		
		public static final String DEL_RECORD = "删除了名为 %s 的%s";
	// -------------Operating-------------------
	
		public static final String UPDATE_DREPORT = "修改了日报";
		public static final String ADD_DREPORT= "提交了日报";
		public static final String DEL_DREPORT = "删除了日报";
		
		public static final String UPDATE_WREPORT = "修改了周报";
		public static final String ADD_WREPORT= "提交了周报";
		public static final String DEL_WREPORT = "删除了周报";
		
		public static final String UPDATE_MREPORT = "修改了月报";
		public static final String ADD_MREPORT= "提交了月报";
		public static final String DEL_MREPORT = "删除了月报";
	
	// --------------Validation---------------------
		
	public static final String VALID_OBJECT_NAME = "目标名称必须存在";
	public static final String VALID_VISIBLE_TYPE= "可见范围必须存在";
	public static final String VALID_OBJECT_OWNER = "负责人必须存在";
	public static final String VALID_OBJECT_CYCLE = "周期必须存在";
	
}
