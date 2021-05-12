package com.gaf.project.response;

import com.gaf.project.model.Class;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data

public class ClassResponse {
    @SerializedName("classes")
    @Expose
    private List<Class> classs=null;
}
