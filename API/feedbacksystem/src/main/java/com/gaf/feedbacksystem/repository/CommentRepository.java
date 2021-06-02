package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.TraineeComment;
import com.gaf.feedbacksystem.entity.TraineeCommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<TraineeComment, TraineeCommentId> {
    @Query("SELECT a FROM TraineeComment a where a.primaryKey.mClass.classID = :idClass and a.primaryKey.module.moduleID = :idModule")
    List<TraineeComment> findByMClassAndModule(@Param("idClass") Integer idClass,
                                                  @Param("idModule") Integer idModule);

    @Query("SELECT a FROM TraineeComment a where a.primaryKey.mClass.classID = :idClass and a.primaryKey.module.moduleID = :idModule and a.primaryKey.trainee.userName = :userName")
    List<TraineeComment> findByMClassAndModuleAndTrainee(@Param("idClass") Integer idClass,
                                               @Param("idModule") Integer idModule,
                                                @Param("userName") String userName);
}
