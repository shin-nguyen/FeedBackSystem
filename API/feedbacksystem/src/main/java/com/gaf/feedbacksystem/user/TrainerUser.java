package com.gaf.feedbacksystem.user;

import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.entity.Trainer;

public class TrainerUser implements BaseUser<Trainer>{
    Trainer trainer;


    @Override
    public void setUser(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public String getPassword() {
        return trainer.getPassword();
    }

    @Override
    public String getUserName() {
        return trainer.getUserName();
    }
}
