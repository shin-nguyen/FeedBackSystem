package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.QuestionDto;
import com.gaf.feedbacksystem.service.IQuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/question")
@Tag(name = "questions")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListActiveQuestion", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> getListActiveQuestion(){
        try{
            List<QuestionDto> questionList = questionService.findByDeleteFalse();
            if ( questionList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("questions", questionList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListQuestion", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> getListQuestion(){
        try{
            List<QuestionDto> questionList = questionService.findAll();
            if ( questionList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("questions", questionList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListQuestionByTopic/{id}", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListQuestionByTopic(@PathVariable("id") Integer topicId){
        try{
            List<QuestionDto> questionList = questionService.findAllByTopicId(topicId);
            if ( questionList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("questions", questionList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PostMapping(value = "/")
    public QuestionDto create(@Valid @RequestBody QuestionDto questionDto){
        try{
            return  questionService.save(questionDto);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question Not Found", exc);
        }
    }


    @PutMapping(value = "/")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public ResponseEntity<QuestionDto> update(@Valid  @RequestBody QuestionDto questionDto){
        try {
            final QuestionDto updatedQuestion = questionService.update(questionDto);

            return ResponseEntity.ok(updatedQuestion);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question Not Found", exc);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public Map<String, Boolean> delete(@PathVariable (name = "id") Integer id) {
        try {
            Map<String, Boolean> response = new HashMap<>();
            try {
                questionService.deleteById(id);
                response.put("deleted", Boolean.TRUE);

            } catch (Exception exception) {
                response.put("deleted", Boolean.FALSE);
            }
            return response;
        } catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question Not Found", exc);
        }
    }
}
