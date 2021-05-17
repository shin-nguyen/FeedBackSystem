package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,String> {

    Trainee findByUserName(String s);
}
