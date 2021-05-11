package com.gaf.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class AssignmentId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Class mClass;

    private Module module;

    private Trainer trainer;

}
