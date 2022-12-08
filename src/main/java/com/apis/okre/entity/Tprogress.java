package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Tprogress implements Serializable {

    private static final long serialVersionUID = 5L;

    public Long pr_id;
    public Long pr_creator;
    public Long pr_parent;
    public Integer pr_parent_type; // 0: object, 1:kr, 2:task, 3:item
    public Integer pr_percent;
    public Integer pr_up_percent;
    public Integer pr_status; // 0 正常, 1有风险,2 已延期
    public String pr_description;
    public String pr_start_date;
    public String pr_end_date;
    public Date created_at;

    public String pr_uploaded_file;
    
    public ArrayList<TFile> pr_files = new ArrayList<TFile>();
    
    public String ob_name;
    public String kr_name;
    public String loginName;
    public String employeeName;

    public Tprogress() {
        super();
    }

}
