package com.gaf.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Serializable {

    @SerializedName("module")
    @Expose
    private Module module;
    @SerializedName("trainee")
    @Expose
    private Trainee trainee;
    @SerializedName("mClass")
    @Expose
    private Class mClass;
    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("value")
    @Expose
    private int value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(module.getModuleID(), answer.module.getModuleID()) &&
                Objects.equals(trainee, answer.trainee) &&
                Objects.equals(mClass, answer.mClass) &&
                Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, trainee, mClass, question);
    }
}
