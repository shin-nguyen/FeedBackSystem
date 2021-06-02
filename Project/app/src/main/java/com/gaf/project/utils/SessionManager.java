package com.gaf.project.utils;

import android.content.Context;

public class SessionManager {

    private static final String USER_NAME ="USER_NAME";
    private static final String REMEMBER_ME ="REMEMBER_ME";
    private static final String IS_LOGIN ="IS_LOGIN";
    private static final String USER_ROLE ="USER_ROLE";

    private MySharedPreferences mySharedPreferences;
    private static SessionManager instance;

    public static void init(Context mContext){
        instance= new SessionManager();
        instance.mySharedPreferences = new MySharedPreferences(mContext);
    }

    public static SessionManager getInstance(){
        if(instance==null){
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUserName(String userName) {
        SessionManager.getInstance().mySharedPreferences.putStringValue(USER_NAME,userName);
    }

    public String getUserName() {
        return SessionManager.getInstance().mySharedPreferences.getStringValue(USER_NAME);
    }

    public void setRememberMe(Boolean rememberMe) {
        SessionManager.getInstance().mySharedPreferences.putBooleanValue(REMEMBER_ME,rememberMe);
    }

    public Boolean getRememberMe() {
        return SessionManager.getInstance().mySharedPreferences.getBooleanValue(REMEMBER_ME);
    }

    public void setIsLogin(Boolean isLogin) {
        SessionManager.getInstance().mySharedPreferences.putBooleanValue(IS_LOGIN,isLogin);
    }

    public Boolean getIsLogin() {
        return SessionManager.getInstance().mySharedPreferences.getBooleanValue(IS_LOGIN);
    }

    public void setUserRole(String role) {
        SessionManager.getInstance().mySharedPreferences.putStringValue(USER_ROLE,role);
    }

    public String getUserRole() {
        return SessionManager.getInstance().mySharedPreferences.getStringValue(USER_ROLE);
    }
}
