package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Otask implements Serializable {

    private static final long serialVersionUID = 4L;

    public Long task_id;
    public String task_name; // 任务名称
    public String task_status; // 1:已完成, 2:进行中, 3:已取消, 4:已延迟, 5:暂停中, 6:未开始
    public Integer task_order; // order of list in kresult
    public String task_start_date; //
    public String task_end_date; //
    public Integer task_priority; // 1:最高 ,2,3,4,5
    public String task_description; // 任务描述
    public Float task_target_price; // 目标值
    public Long task_unit; // 单位 of tb_unit.id
    public Float task_real_price; // 实际值
    public String task_feedback_time; // 反馈时间
    public Float task_est_worktime; // 预估工时
    public Long task_vice_leader; // 代领导发起
    public Long task_creator; // 发起者 of tb_user.id
    public Long task_owner; // 负责人 of tb_user.id
    public Long task_approver; // 审批人 of tb_user.id
    public String task_collaborator; // 参与人 of tb_user.id
    public Long task_parent_object; // of tb_kr.id
    public Long task_parent_kr; // of tb_kr.id
    public Long task_parent_item; // of tb_item.id
    public Long task_parent_task; // 母任务 of tb_task.id
    public Long task_parent_review;
    public String task_refer; // 关联其他 目标, 项目 ex: ",o12,o13,i31,i7,"
    public Integer task_visible_type; // 可见范围: 公开 部门可见 仅相关成员可见
    public String task_visible_range; // ***可见范围: ex: ",,"
    public String task_uploaded_files; // 任务附件 url list
    public Long task_milestone; // parent 里程碑(milestone) of tb_milestone.id
    public Long task_kanban; // parent kanban of tb_kanban.id
    public Integer task_progress;
    public Date created_at;
    public Date updated_at;

    public ArrayList<Worktime> task_worktimes = new ArrayList<Worktime>();
    public ArrayList<Otask> task_tasks = new ArrayList<Otask>();
    public ArrayList<TFile> task_files = new ArrayList<TFile>();
    public ArrayList<Tprogress> task_progresses = new ArrayList<Tprogress>();
    public String task_parent_item_name;
    public String task_parent_object_name;
    public String task_owner_name;
    public String task_creator_name;
    public String task_collaborator_names;
    public String task_subtask_status;
    public User operator;
    public String task_owners;
    public String task_dept_name;
    
    public String task_sortby;

    public Otask() {
        super();
    }

}
