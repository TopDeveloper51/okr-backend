package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Directory implements Serializable {

    private static final long serialVersionUID = 5234234L;

    public Long dir_id;
    public String dir_name;
    public Long dir_creator;
    public Integer dir_parent_type;
    public Long dir_parent;
    public Long dir_parent_dir;
    public Date created_at;
    
    public ArrayList<TFile> dir_files = new ArrayList<TFile>();
    public ArrayList<Directory> dir_dirs = new ArrayList<Directory>();
    
    public Directory() {
        super();
    }
    
}
