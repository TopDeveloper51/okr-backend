package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long id;
    public String employeeName;
    public String loginName;
    public String[] postName;
    public String postNameStr;
    public String email;
    public String phone;
    public String user_password;
    public String user_avatar_url;
    public String user_role;
    public String user_company_name;
    public Long deptId;
    public Long user_dot_superior;
    public String user_attentions;
    public String user_recent_items;
    public Long user_superior;
    public Long user_company_id;
    public Integer status;
    public Date created_at;

    public String user_api_token;
    public String user_post_address;

    public String departmentOaName;
    public String user_dp_serial;
    public User operator;
    public String leader_dps; // user's dp_leader of tb_department.
    public ArrayList<User> user_be_attentions = new ArrayList<User>();
    public ArrayList<User> user_visitors = new ArrayList<User>();
    public Integer user_visit_counts;
    public String ids;
    
    public String user_sso;

    public User() {
        super();
    }

}
