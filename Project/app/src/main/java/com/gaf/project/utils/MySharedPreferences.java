package com.gaf.project.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gaf.project.model.Trainee;
import com.google.gson.Gson;

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

    public void putStringValue(String key,String value){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public void setTrainee(String key, Trainee trainee){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(trainee);
        editor.putString(key, json);
        editor.apply();
    }
    public Trainee getTrainee(String key){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json =  sharedPreferences.getString(key,"");
        Trainee trainee = gson.fromJson(json, Trainee.class);
        return trainee;
    }

}
