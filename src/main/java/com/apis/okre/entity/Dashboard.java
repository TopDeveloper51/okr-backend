package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Dashboard implements Serializable {

    private static final long serialVersionUID = 2236234L;

	public String req_cycle;
	public String req_dp_serial;
	public String req_option;
	public Integer req_index;
	public String req_date;
	
	public Integer req_item_id;
    
    public Dashboard() {
        super();
    }
    
}
