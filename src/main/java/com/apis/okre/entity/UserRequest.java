package com.apis.okre.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.*;


public class UserRequest implements Serializable {

    private static final long serialVersionUID = 11L;

    @NotEmpty
    public String userPhone;
        
    @NotEmpty
    public String userPassword;
    		  
    public UserRequest() {
        super();
    }


}
