package com.gaf.project.utils;

import com.gaf.project.config.RetrofitConfig;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Question;
import com.gaf.project.service.AdminService;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.AuthenticationService;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.service.ModuleService;
import com.gaf.project.service.QuestionService;
import com.gaf.project.service.TopicService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.service.TypeFeedbackService;

public class ApiUtils {

    //Dia chi may host
    public static final String BASE_URL = "http://192.168.1.198:8080/";

    public static AuthenticationService getAuthenticationService() {
        return RetrofitConfig.getInstance().buildRetrofit().create(AuthenticationService.class);
    }

    public static ModuleService getModuleService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(ModuleService.class);
    }
    public static AdminService getAdminService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(AdminService.class);
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

    public static QuestionService getQuestionService() {
        return RetrofitConfig.getInstance().
                builderRetrofitAuth()
                .create(QuestionService.class);
    }

    public static TrainerService getTrainerService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(TrainerService.class);
    }

    public static TopicService getTopicService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(TopicService.class);
    }

    public static FeedbackService getFeedbackService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(FeedbackService.class);
    }

    public static TypeFeedbackService getTypeFeedbackService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(TypeFeedbackService.class);
    }

    public static AnswerService getAnswerService() {
        return RetrofitConfig.getInstance()
                .builderRetrofitAuth()
                .create(AnswerService.class);
    }
}