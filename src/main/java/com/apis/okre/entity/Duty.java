package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Duty implements Serializable {

    private static final long serialVersionUID = 645353L;

    public Long duty_id;
    public String duty_name;
    public String duty_code;
    public String duty_type;
    public String duty_serial_num;
    public String duty_description;
    public Integer duty_status;
    public Date created_at;
    
    public Duty() {
        super();
    }
    
}
