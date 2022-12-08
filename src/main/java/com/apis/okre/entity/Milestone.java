package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Milestone implements Serializable {

    private static final long serialVersionUID = 21231L;

    public Long ms_id;
    public String ms_name;
    public String ms_description;
    public Integer ms_progress;
    public String ms_start_date;
    public String ms_end_date;
    public Long ms_owner;
    public Long ms_parent_item;
    public String ms_task;
    public Date created_at;
    public Date updated_at;

    public ArrayList<Otask> ms_tasks = new ArrayList<Otask>();

    public Milestone() {
        super();
    }

}
