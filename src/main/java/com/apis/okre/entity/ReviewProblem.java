package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class ReviewProblem implements Serializable {

    private static final long serialVersionUID = 12332L;

    public Long rp_id;
    public Long rp_parent;
    public String rp_problem;
    public String rp_reason;
    public String rp_solution;
    public Date created_at;

    public ReviewProblem() {
        super();
    }

}
