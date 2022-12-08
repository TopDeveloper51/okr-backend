package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Department implements Serializable {

    private static final long serialVersionUID = 15L;

    public Long id;
    public String departmentOaName;
    public String departmentShortName;
    public String dp_leader;
    public Long pid;
    public String dp_serial_id;
    public Integer dp_order_number;
    public Integer status;
    public Date created_at;

    public ArrayList<Department> dp_departments = new ArrayList<Department>();
    public ArrayList<User> dp_users = new ArrayList<User>();

    public Department() {
        super();
    }

}
