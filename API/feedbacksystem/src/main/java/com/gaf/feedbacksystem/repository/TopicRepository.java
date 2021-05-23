package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Integer> {
    Topic findByTopicID(Integer TopicId);
}
