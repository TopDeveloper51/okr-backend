package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Kanban implements Serializable {

    private static final long serialVersionUID = 5234234L;

    public Long kb_id;
    public String kb_name;
    public Integer kb_order;
    public Long kb_parent_item;
    public String kb_task;
    public Date created_at;
    
    public Integer kb_old_order;
    
    public ArrayList<Otask> kb_tasks = new ArrayList<Otask>();
    
    public Kanban() {
        super();
    }
    
}
