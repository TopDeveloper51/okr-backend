package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Setting implements Serializable {

    private static final long serialVersionUID = 2126812L;

    public Long st_id;
    public String st_value;
    public Long st_category_id;
    public Integer st_category_type;
    public Date created_at;
    
    public Setting() {
        super();
    }
    
}
