package com.gaf.feedbacksystem.repository;


import com.gaf.feedbacksystem.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface ModuleRepository  extends JpaRepository<Module,Integer> {

    @Transactional
    @Modifying
    @Query("update Module set isDeleted =true where moduleID = :id ")
    void deleteByModuleId(@Param("id") Integer id);

    @Query("from Module  where isDeleted=false")
    List<Module> findAllByDeletedIsFalse();

    @Query("select m from Module m join Assignment a on m.moduleID = a.primaryKey.module.moduleID and a.primaryKey.trainer.userName = :userName and m.isDeleted=false")
    List<Module> findAllByTrainer(@Param("userName") String userName);

    @Query("select m from Module m  join Assignment assignment on m.moduleID = assignment.primaryKey.module.moduleID join TraineeAssignment ta on ta.primaryKey.assignment.registrationCode = assignment.registrationCode and ta.primaryKey.trainee.userName = :userName and m.isDeleted=false ")
    List<Module> findAllByTrainee(@Param("userName") String userName);

    Module findByModuleID(Integer id);
}
