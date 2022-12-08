package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.*;


public class Pagination implements Serializable {

    private static final long serialVersionUID = 1512323L;

    public Integer page_totals;			// total counts of list
    public Integer page_start;			// list index to start show page
    public Integer page_counts;			// counts per a page
    		  
    public Pagination() {
        super();
    }


}
