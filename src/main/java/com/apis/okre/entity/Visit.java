package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Visit implements Serializable {

    private static final long serialVersionUID = 152143L;

    public Long vs_id;
    public Long vs_visitor;
    public String vs_description;
    public Integer vs_target_type;
    public Long vs_target;
    public Integer vs_counts;
    public Date created_at;
        
    public Visit() {
        super();
    }
    
}
