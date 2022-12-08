package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Intercom implements Serializable {

    private static final long serialVersionUID = 45L;

    public Long ic_id;
    public Long ic_sender;
    public Long ic_receiver;
    public String ic_description;
    public Integer ic_parent_type;
    public Long ic_parent;
    public Long ic_reply_ic;
    public String ic_thumbup;
    public Date created_at;
    
    public String ic_uploaded_file;
    
    public ArrayList<TFile> ic_files = new ArrayList<TFile>();
    public ArrayList<Intercom> ic_replys = new ArrayList<Intercom>();
    public String ic_sender_name;
    public String ic_receiver_name;
    
    public Intercom() {
        super();
    }

}
