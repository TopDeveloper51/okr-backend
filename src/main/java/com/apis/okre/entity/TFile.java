package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class TFile implements Serializable {

    private static final long serialVersionUID = 5242323L;

    public Long file_id;
    public Long file_owner;
    public String file_name;
    public String file_uri;
    public String file_downUri;
    public String file_type;
    public Long file_size;
    public Date created_at;
    
    public Integer file_parent_type;
    public Long file_parent;
    public Long file_parent_dir;
    
    public String file_dir;
    public TFile() {
        super();
    }
    
}
