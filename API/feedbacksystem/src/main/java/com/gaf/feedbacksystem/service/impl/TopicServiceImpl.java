package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.TopicDto;
import com.gaf.feedbacksystem.entity.Topic;
import com.gaf.feedbacksystem.repository.TopicRepository;
import com.gaf.feedbacksystem.service.ITopicService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements ITopicService {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public List<TopicDto> findAll() {
        List<Topic> topic = topicRepository.findAll();
        List<TopicDto> topicDto = ObjectMapperUtils.mapAll(topic,TopicDto.class);
        return topicDto;
    }

    @Override
    public List<TopicDto> findAllByTopicName() {
        List<Topic> topics = topicRepository.findAllByOrderByTopicNameAsc();
        List<TopicDto> topicDto = ObjectMapperUtils.mapAll(topics,TopicDto.class);
        return topicDto;
        //var sort = new Sort(Sort.Direction.ASC, "topicName");
        //return topicRepository.findAllOrderByTopicName(sort);
    }

    @Override
    public TopicDto findById(Integer topicId) {
        Topic topic = topicRepository.findByTopicID(topicId);
        TopicDto topicDto = ObjectMapperUtils.map(topic,TopicDto.class);
        return topicDto;
    }
}
