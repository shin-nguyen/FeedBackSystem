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

//    @Query("select t from Trainee_Assignment t where t.primaryKey.trainee.username = : username and t.primaryKey.assignment.registrationCode =: code")
//    void compare(@Param("username")String username, @Param("code") String code);
}
