package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.TopicDto;

import java.util.List;

public interface ITopicService {
    List<TopicDto> findAll();

    List<TopicDto> findAllByTopicName();

    TopicDto findById(Integer topicId);
}
