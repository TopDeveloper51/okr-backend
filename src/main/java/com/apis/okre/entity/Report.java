package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Report implements Serializable {

    private static final long serialVersionUID = 6134332L;
    
    public Long rp_id;
    public String rp_date;
    public Integer rp_type;
    public String rp_abstract;
    public String rp_todo;
    public String rp_attatch_files;
    public Integer rp_visible_range;
    public String rp_ta_contacts;
    public Integer rp_send_check;
    public Integer rp_publish_type;
    public Long rp_creator;
    public String rp_tag;
    public Date created_at;
    
    public Integer rp_progress_tasks;
    public Integer rp_expired_tasks;
    public Integer rp_completed_tasks;
    
    public ArrayList<TFile> rp_files = new ArrayList<TFile>();
    
    public Report() {
        super();
    }
    
}
