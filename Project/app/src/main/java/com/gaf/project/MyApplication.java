package com.gaf.project;


import android.app.Application;
import android.widget.Toast;

import com.gaf.project.utils.SessionManager;

public class MyApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.init(getApplicationContext());
    }
    //show toast
    private void showToast(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }


}
