package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Execute implements Serializable {

    private static final long serialVersionUID = 52143L;

    public Long e_id;
    public String e_name;
    public String e_owner_name;
    public String e_dp_name;
    public Integer e_type;			//0: item, 1:task
    public String e_ob_name;		
    
    
    public Execute() {
        super();
    }
    
}
