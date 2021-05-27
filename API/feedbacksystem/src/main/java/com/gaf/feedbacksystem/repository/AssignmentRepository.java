package com.gaf.feedbacksystem.repository;


import com.gaf.feedbacksystem.entity.Assignment;
import com.gaf.feedbacksystem.entity.AssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentId> {
    @Query("SELECT a FROM Assignment a WHERE a.primaryKey.trainer.userName= :username")
    List<Assignment> findByTrainerUserName(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("delete from Assignment where primaryKey.mClass.classID = :idClass and primaryKey.module.moduleID = :idModule and primaryKey.trainer.userName = :userName")
    void deleteByMClassAndModuleAndTrainer(@Param("idClass") Integer idClass,
                                           @Param("idModule") Integer idModule,
                                           @Param("userName") String userName);
}
