package com.gaf.project.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeFeedback {
    private Integer typeID;
    private String typeName;
    private  boolean isDeleted;
}
