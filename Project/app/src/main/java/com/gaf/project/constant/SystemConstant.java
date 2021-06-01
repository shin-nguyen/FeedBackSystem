package com.gaf.project.constant;

import android.graphics.Color;

import com.gaf.project.authentication.AuthenticationResponse;

import java.util.Optional;

public class SystemConstant {
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String TRAINEE_ROLE = "TRAINEE";
    public static final String TRAINER_ROLE = "TRAINER";

    public static final String ADD    = "ADD";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String DETAIL = "DETAIL";


    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    public static AuthenticationResponse authenticationResponse = new AuthenticationResponse();

    public static final int []color ={
            Color.parseColor("#FF4500"),
            Color.parseColor("#FF6347"),
            Color.parseColor("#FF7F50"),
            Color.parseColor("#FF8C00"),
            Color.parseColor("#FFA500")
    };

    public static final int lengthColor = color.length;
}

