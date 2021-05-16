package com.gaf.feedbacksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaf.feedbacksystem.entity.Class;

@Repository
public interface ClazzRepository extends JpaRepository<Class, Integer> {
    Class save(Class aClazz);

    Class findByClassID(Integer classID);

    @Query("update Class set isDeleted =true where classID = :id ")
    void deleteByClassId(@Param("id") String id);
}
