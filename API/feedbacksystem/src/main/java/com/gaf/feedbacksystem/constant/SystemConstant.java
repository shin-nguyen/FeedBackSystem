package com.gaf.feedbacksystem.constant;

import org.hibernate.usertype.UserType;

public class SystemConstant {
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String TRAINEE_ROLE = "TRAINEE";
    public static final String TRAINER_ROLE = "TRAINER";
    public static String USER = ADMIN_ROLE;

    public static final Integer SHORT_EXP = 1000 * 60 * 30 ;
    public static final Integer LONG_EXP = 1000 * 60 * 60 * 24;

}
