package com.gaf.feedbacksystem.entity;


import com.gaf.feedbacksystem.user.TraineeUser;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.RowId;

import javax.persistence.*;
import java.util.Collection;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table
@AssociationOverrides({

        @AssociationOverride(name = "primaryKey.class",
                joinColumns = @JoinColumn(name = "classID")),
        @AssociationOverride(name = "primaryKey.trainer",
                joinColumns = @JoinColumn(name = "trainerID",referencedColumnName = "username")),
        @AssociationOverride(name = "primaryKey.module",
                joinColumns = @JoinColumn(name = "moduleID")),
})
public class Assignment{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    AssignmentId primaryKey = new AssignmentId();

    private AssignmentId getPrimaryKey(){
        return primaryKey;
    }
    @Transient
    public Class getmClass() {
        return getPrimaryKey().getMClass();
    }
    public void setmClass(Class mClass) {
        getPrimaryKey().setMClass(mClass);
    }

    @Transient
    public Module getModule() {
        return getPrimaryKey().getModule();
    }
    public void setModule(Module module) {
        getPrimaryKey().setModule(module);
    }

    @Transient
    public Trainer getTrainer() {
        return getPrimaryKey().getTrainer();
    }
    public void setTrainer(Trainer trainer) {
        getPrimaryKey().setTrainer(trainer);
    }


    @Column(length = 50, nullable = false, unique = true)
    private String registrationCode;

}

