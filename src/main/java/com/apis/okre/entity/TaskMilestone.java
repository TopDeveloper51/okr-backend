package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class TaskMilestone implements Serializable {

    private static final long serialVersionUID = 523232L;

    public String ms_name;
    public String ms_start_date;
    public String ms_end_date;
    public Integer resAll;
    public Integer resNotcomplete;
    public Integer res10;
    public Integer res11;
    public Integer resComplete;
    public Integer res20;
    public Integer res21;
    public Integer resAvg;

    public TaskMilestone() {
        super();
    }

}
