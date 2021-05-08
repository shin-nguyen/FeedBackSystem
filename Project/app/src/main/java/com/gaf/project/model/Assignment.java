package com.gaf.project.model;

public class Assignment {
    private int id;
    private String course_name;
    private String class_name;
    private String trainer_name;
    private String registration_code;

    public Assignment(int id, String course_name, String class_name, String trainer_name, String registration_code) {
        this.id = id;
        this.course_name = course_name;
        this.class_name = class_name;
        this.trainer_name = trainer_name;
        this.registration_code = registration_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTrainer_name() {
        return trainer_name;
    }

    public void setTrainer_name(String trainer_name) {
        this.trainer_name = trainer_name;
    }

    public String getRegistration_code() {
        return registration_code;
    }

    public void setRegistration_code(String registration_code) {
        this.registration_code = registration_code;
    }
}
