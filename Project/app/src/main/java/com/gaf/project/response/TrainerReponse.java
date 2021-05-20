package com.gaf.project.response;

import com.gaf.project.model.Module;
import com.gaf.project.model.Trainer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class TrainerReponse {
    @SerializedName("trainers")
    @Expose
    private List<Trainer> trainers=null;
}
