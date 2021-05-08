package com.gaf.project.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private  static final String MY_SHARED_FREFERENCES = "MY_SHARED_FREFERENCES";
    private Context mContext;

    public MySharedPreferences(Context mContext){
        this.mContext = mContext;
    }

    public void putIntValue(String key,Integer value){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public Integer getIntValue(String key){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,-1);
    }

    public void putBooleanValue(String key,Boolean value){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
}
