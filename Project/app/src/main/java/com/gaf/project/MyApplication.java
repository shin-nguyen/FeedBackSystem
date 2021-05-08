package com.gaf.project;


import android.app.Application;

import com.gaf.project.utils.SessionManager;

public class MyApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.init(getApplicationContext());
    }
}
