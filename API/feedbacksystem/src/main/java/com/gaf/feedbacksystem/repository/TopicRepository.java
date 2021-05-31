package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.dto.TopicDto;
import com.gaf.feedbacksystem.entity.Topic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Integer> {

    Topic findByTopicID(Integer TopicId);

    List<Topic> findAllByOrderByTopicNameAsc();
}
