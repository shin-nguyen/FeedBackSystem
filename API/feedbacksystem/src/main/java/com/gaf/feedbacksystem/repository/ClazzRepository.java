package com.gaf.feedbacksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaf.feedbacksystem.entity.Class;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClazzRepository extends JpaRepository<Class, Integer> {

    @Query("from Class  where isDeleted=false")
    List<Class> findAllByDeletedIsFalse();

    @Query("select c from Class c join Assignment a on c.classID = a.primaryKey.mClass.classID and a.primaryKey.trainer.userName = :userName")
    List<Class> findAllByTrainer(@Param("userName") String userName);
    
    Class save(Class aClazz);
    Class findByClassID(Integer classID);
    @Transactional
    @Modifying
    @Query("update Class set isDeleted =true where classID = :id ")
    void deleteByClassId(@Param("id") Integer id);
}
