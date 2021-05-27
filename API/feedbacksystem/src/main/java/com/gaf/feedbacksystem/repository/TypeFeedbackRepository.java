package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.TypeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFeedbackRepository extends JpaRepository<TypeFeedback,Integer> {
}
