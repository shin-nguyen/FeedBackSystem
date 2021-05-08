package com.gaf.feedbacksystem.user;

import com.gaf.feedbacksystem.entity.Admin;

public class AdminUser implements BaseUser<Admin> {

    Admin admin;


    @Override
    public void setUser(Admin user) {
        this.admin = user;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUserName() {
        return admin.getUserName();
    }
}
