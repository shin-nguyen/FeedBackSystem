package com.gaf.feedbacksystem.user;



public interface BaseUser<T>  {

    void setUser(T user);

    String getPassword();

    String getUserName();
}
