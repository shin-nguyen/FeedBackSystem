package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.QuestionDto;
import com.gaf.feedbacksystem.entity.Assignment;
import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.Question;
import com.gaf.feedbacksystem.repository.QuestionRepository;
import com.gaf.feedbacksystem.service.IQuestionService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public List<QuestionDto> findAll() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = ObjectMapperUtils.mapAll(questions,QuestionDto.class);
        return questionDtos;
    }

    @Override
    public List<QuestionDto> findByDeleteFalse() {
        List<Question> questions = questionRepository.findAllByDeletedFalse();
        List<QuestionDto> questionDtos = ObjectMapperUtils.mapAll(questions,QuestionDto.class);
        return questionDtos;
    }

    @Override
    public List<QuestionDto> findAllByTopicId(Integer id) {
        List<Question> questions = questionRepository.findAllByTopic_TopicID(id);
        List<QuestionDto> questionDtos = ObjectMapperUtils.mapAll(questions,QuestionDto.class);
        return questionDtos;
    }

    @Override
    public QuestionDto findById(Integer QuestionId) {
        Question question = questionRepository.findByQuestionID(QuestionId);
        QuestionDto questionDto = ObjectMapperUtils.map(question,QuestionDto.class);
        return questionDto;
    }

    @Override
    public QuestionDto update(QuestionDto questionDto) {
        Question question=  questionRepository.findByQuestionID(questionDto.getQuestionID());

        question.setQuestionContent(questionDto.getQuestionContent());
        question.setTopic(question.getTopic());

        return ObjectMapperUtils.map(questionRepository.save(question), QuestionDto.class);
    }

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        Question  question = ObjectMapperUtils.map(questionDto,Question.class);

        return ObjectMapperUtils.map(questionRepository.save(question),QuestionDto.class);
    }

    @Override
    public void deleteById(Integer id) {
        questionRepository.deleteByQuestionId(id);
    }
}
