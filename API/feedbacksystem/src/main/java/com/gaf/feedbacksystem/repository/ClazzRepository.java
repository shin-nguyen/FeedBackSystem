package com.gaf.feedbacksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaf.feedbacksystem.entity.Class;

@Repository
public interface ClazzRepository extends JpaRepository<Class, String> {
    Class save(Class aClazz);
    Class findByClassID(String classID);
}
