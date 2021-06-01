package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Answer;
import com.gaf.feedbacksystem.entity.AnswerID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, AnswerID> {

    @Query("SELECT a FROM Answer a where a.primaryKey.mClass.classID = :idClass and a.primaryKey.module.moduleID = :idModule")
    List<Answer> findByMClassAndModule(@Param("idClass") Integer idClass,
                                       @Param("idModule") Integer idModule);

    @Query("SELECT a FROM Answer a where a.primaryKey.mClass.classID = :idClass and a.primaryKey.module.moduleID = :idModule and a.primaryKey.question.questionID = :idQuestion")
    List<Answer> findByMClassAndModuleAndQuestion(@Param("idClass") Integer idClass,
                                                  @Param("idModule") Integer idModule,
                                                  @Param("idQuestion") Integer idQuestion);
}