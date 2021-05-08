package com.gaf.feedbacksystem.user;

import com.gaf.feedbacksystem.entity.Trainee;

public class TraineeUser implements BaseUser<Trainee>{
    Trainee trainee;


    @Override
    public void setUser(Trainee user) {
        this.trainee = user;
    }

    @Override
    public String getPassword() {
        return trainee.getPassword();
    }

    @Override
    public String getUserName() {
        return trainee.getUserName();
    }
}
