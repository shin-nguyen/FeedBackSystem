package com.gaf.project.utils;

import com.gaf.project.config.RetrofitConfig;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.AuthenticationService;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.ModuleService;

public class ApiUtils {

    //Dia chi may host
    public static final String BASE_URL = "http://192.168.1.3:8080/";

    public static AuthenticationService getAuthenticationService() {
        return RetrofitConfig.getInstance().buildRetrofit().create(AuthenticationService.class);
    }

    public static ModuleService getModuleService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(ModuleService.class);
    }

    public static ClassService getClassService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(ClassService.class);
    }

    public static AssignmentService getAssignmentService() {
        return RetrofitConfig.getInstance().
                builderRetrofitAuth()
                .create(AssignmentService.class);
    }


}