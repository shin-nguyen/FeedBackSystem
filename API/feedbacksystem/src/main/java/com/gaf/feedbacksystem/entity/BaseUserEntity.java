package com.gaf.feedbacksystem.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Data
public class BaseUserEntity {
    @Id
    @Column(name = "username")
    private  String userName;
    private String name;
    private  String email;
    private String password;
}
