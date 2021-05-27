package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,String> {

    Trainee findByUserName(String s);
}
