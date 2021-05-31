package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.TraineeAssignment;
import com.gaf.feedbacksystem.entity.TraineeAssignmentID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeAssignmentRepository extends JpaRepository<TraineeAssignment, TraineeAssignmentID> {

//    @Query("")
//    TraineeAssignment compare(@Param("username")String username, @Param("code") String code);
    @Query("from TraineeAssignment  t where  t.primaryKey.assignment.registrationCode =:code and t.primaryKey.trainee.userName =:username")
    TraineeAssignment compare(@Param("username")String username, @Param("code") String code);
}
