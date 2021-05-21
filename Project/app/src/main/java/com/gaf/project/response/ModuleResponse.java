package com.gaf.project.response;


import com.gaf.project.model.Module;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ModuleResponse {
    @SerializedName("modules")
    @Expose
    private List<Module> modules=null;
}
