package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Review implements Serializable {

    private static final long serialVersionUID = 3214L;

    public Long re_id;
    public Long re_parent;
    public Integer re_parent_type;
    public String re_description;
    public String re_problem;
    public String re_collaborators;
    public Date created_at;

    public ArrayList<ReviewProblem> re_problems = new ArrayList<ReviewProblem>();
    public ArrayList<Otask> re_tasks = new ArrayList<Otask>();

    public Review() {
        super();
    }
}
