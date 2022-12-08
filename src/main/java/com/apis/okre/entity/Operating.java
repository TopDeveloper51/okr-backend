package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Operating implements Serializable {

    private static final long serialVersionUID = 25L;

    public Long op_id;
    public Long op_operator;
    public String op_description;
    public Integer op_parent_type;
    public Long op_parent;
    public Date created_at;
    
    public String op_operator_name;

    public Operating() {
        super();
    }

}
