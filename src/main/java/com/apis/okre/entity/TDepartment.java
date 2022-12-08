package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class TDepartment implements Serializable {

    private static final long serialVersionUID = 15L;
    
    public Long id;
    public String departmentOaName;
    public Long pid;
    public String serial_id;
    public Integer status;
    public Date created_at;

    public TDepartment() {
        super();
    }

}
