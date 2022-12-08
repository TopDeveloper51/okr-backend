package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Kresult implements Serializable {

    private static final long serialVersionUID = 3L;

    public Long kr_id;
    public String kr_name;
    public Long kr_owner;
    public User kr_owners; // appex
    public Long kr_creator;
    public Long kr_parent_object;
    public Integer kr_order;
    public Integer kr_completion;
    public Integer kr_confidence; // 1~10
    public Float kr_score;
    public String kr_score_description;
    public Long kr_ta;
    public String kr_start_date;
    public String kr_end_date;
    public Date created_at;
    public Float kr_rate;
    public String kr_description;
    
    public ArrayList<Otask> kr_tasks = new ArrayList<Otask>();
    public ArrayList<Oitem> kr_items = new ArrayList<Oitem>();
    public Long kr_operator;
    public String ob_name;		//parent object name
    public String kr_owner_name;
    public String kr_department_name;
    
    public Integer kr_completion_status;
    
    public Tprogress kr_recent_pr;
    
    public Kresult() {
        super();
    }

}
