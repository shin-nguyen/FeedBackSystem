package com.gaf.project.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment implements Serializable{

    @SerializedName("registrationCode")
    @Expose
    private String registrationCode;
    @SerializedName("module")
    @Expose
    private Module module;
    @SerializedName("trainer")
    @Expose
    private Trainer trainer;
    @SerializedName("mClass")
    @Expose
    private Class mClass;
}