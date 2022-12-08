package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Team implements Serializable {

    private static final long serialVersionUID = 52143L;

    public Long team_id;
    public String team_name;
    public String team_leaders;
    public String team_collaborates;
    public String team_members;
    public Long team_company_id;
    public Date created_at;
    
    public ArrayList<User> team_leaders_users = new ArrayList<User>();
    public ArrayList<User> team_leaders_collaborates = new ArrayList<User>();
    
    public Team() {
        super();
    }
    
}
