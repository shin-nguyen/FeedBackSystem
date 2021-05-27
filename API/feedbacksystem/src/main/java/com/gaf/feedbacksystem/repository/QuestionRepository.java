package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Question;
import com.gaf.feedbacksystem.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

    Question findByQuestionID(Integer questionID);

    List<Question> findAllByTopic_TopicID(Integer id);

}
