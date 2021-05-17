package com.gaf.feedbacksystem.repository;


import com.gaf.feedbacksystem.dto.TrainerDto;
import com.gaf.feedbacksystem.entity.Assignment;
import com.gaf.feedbacksystem.entity.AssignmentId;
import com.gaf.feedbacksystem.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentId> {
//    List<Assignment> findByTrainer(Optional<TrainerDto> trainer);
    @Query("SELECT a FROM Assignment a WHERE a.primaryKey.trainer.userName= :username")
    List<Assignment> findByTrainerUserName(@Param("username") String username);
}
