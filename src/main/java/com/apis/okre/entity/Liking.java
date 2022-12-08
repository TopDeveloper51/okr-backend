package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Liking implements Serializable {

    private static final long serialVersionUID = 7634243L;

    public Long like_id;
    public Integer like_parent_type;
    public Long like_parent;
    public String like_tag;
    public String like_bonus;
    public String like_favor;
    public String like_thumb;
    public Date created_at;
    public Date updated_at;
    
    public Liking() {
        super();
    }
    
}
