package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Worktime implements Serializable {

    private static final long serialVersionUID = 5124523L;

    public Long wt_id;
    public String wt_name;
    public String wt_description;
    public String wt_date;
    public Float wt_hours;
    public String wt_upload_files;
    public Long wt_parent_task;
    public Date created_at;
    
    public Long wt_creator;
    
    public ArrayList<TFile> wt_files = new ArrayList<TFile>();
    
    public Worktime() {
        super();
    }
    
}
