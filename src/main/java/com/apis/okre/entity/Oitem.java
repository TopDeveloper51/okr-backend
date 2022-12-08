package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Oitem implements Serializable {

    private static final long serialVersionUID = 5L;

    public Long item_id;
    public String item_name;
    public String item_start_date;
    public String item_end_date;
    public Long item_owner;
    public String item_participant;
    public String item_followers;
    public String item_approver;
    public String item_description;
    public Integer item_progress;
    public Integer item_visible_range;
    public Long item_company_id;
    public String item_tag;
    public String item_uploaded_file;
    public String item_parent_object;
    public Integer item_parent_kr;
    public Date created_at;
    public Date updated_at;
    public Integer item_status;
    public Integer item_mstatus;
    public Long item_creator;
    public User item_operator;

    public String item_recents;
    public String item_task_status;
    public String dp_serial;
    public ArrayList<Krobject> item_parent_objects = new ArrayList<Krobject>();
    public ArrayList<Tag> item_tags = new ArrayList<Tag>();
    public ArrayList<TFile> item_files = new ArrayList<TFile>();
    
    public ArrayList<User> item_participants= new ArrayList<User>();// 参与人 names
    public ArrayList<User> item_followerses= new ArrayList<User>(); // 关注人 names
    
    public ArrayList<Milestone> item_milestones = new ArrayList<Milestone>();
    public ArrayList<Otask> item_tasks = new ArrayList<Otask>();
    
    public Integer item_del_mode;	//0,1,2 #51card
    
    public Oitem() {
        super();
    }

}
