package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

}
