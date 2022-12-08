package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Tag implements Serializable {

    private static final long serialVersionUID = 52143L;

    public Long tag_id;
    public String tag_name;
    public Integer tag_color;
    public Date created_at;
        
    public Tag() {
        super();
    }
    
}
