package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import com.apis.okre.entity.*;

public class Krobject implements Serializable {

  private static final long serialVersionUID = 122L;

  public Long ob_id;
  public String ob_name;
  public Long ob_owner; // 负责人 of tb_user.id
  public Long ob_creator; // of tb_user.id
  public String ob_start_date;
  public String ob_end_date;
  public Long ob_parent_object; // 对齐目标 of tb_object.id
  public Long ob_parent_kr; // 对齐目标 of tb_kresult.id
  public Long ob_company_id; // tb_department.id of root
  public Integer ob_visible_type; // 0:全公司, 1:仅相关成员 2:仅直接下属 3:指定范围
  public String ob_visible_range; //
  public String ob_participant; // 参与人
  public String ob_attention; // 关注人
  public Integer ob_status; // 结束目标 全部状态(0,1),进行中(0),已结束(1),
  public Integer ob_progress; // 0~100
  public Integer ob_auto_progress; // 设置目标完成度自动更新 0:manual 1:根据关键成果KR完成度自动更新, 2: 根据子目标完成度自动更新
  public Float ob_score;
  public String ob_cycle; // 周期 年度: 2021 第四季度:2021/4 3月: 2021-3
  public Integer ob_mstatus;
  public Date created_at;
  public Date updated_at;
  
  public Integer ob_type;		//0:公司 1:部门 2:团队 3:个人
  public Long ob_type_department;	//所属部门 when ob_type is 1:部门 
  
  public User ob_owners;
  public Krobject ob_parent_objects;
  public ArrayList<Krobject> ob_child_objects = new ArrayList<Krobject>(); // child objects
  public ArrayList<Kresult> ob_results = new ArrayList<Kresult>(); // child key-results
  public ArrayList<Oitem> ob_items = new ArrayList<Oitem>();
  public ArrayList<Otask> ob_tasks = new ArrayList<Otask>();
  public String ob_operator;
  public ArrayList<User> ob_participants = new ArrayList<User>();
  public ArrayList<User> ob_attentions = new ArrayList<User>();
  public String ob_owner_name;
  public String ob_department_name;
  public String ob_visibility_user_names;
  public String ob_visibility_dp_names;
  public Tprogress ob_recent_pr;

  public User operator;
  public String ob_sortby;
  
  public Krobject() {
    super();
  }

}
