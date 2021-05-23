package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,String> {
    Trainer findByUserName(String s);
}
