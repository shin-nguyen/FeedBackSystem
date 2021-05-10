package com.gaf.project.model;


import com.gaf.project.model.Module;
import com.gaf.project.model.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public  class AnswerID implements java.io.Serializable {

    private Class mClass;

    private Module module;

    private Trainee trainee;

    private Question question;
}