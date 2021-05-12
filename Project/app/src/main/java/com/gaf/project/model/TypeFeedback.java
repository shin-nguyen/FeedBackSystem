package com.gaf.project.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeFeedback {
    @SerializedName("typeID")
    private Integer typeID;
    @SerializedName("typeName")
    private String typeName;
    @SerializedName("deleted")
    private  boolean isDeleted;
}
