package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.AnswerDto;
import com.gaf.feedbacksystem.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/answer")
public class AnswerController {

    @Autowired
    private IAnswerService iAnswerService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListAnswer/{idClass}/{idModule}", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListAnswer(@PathVariable(value = "idClass") Integer classId,
                                                               @PathVariable(value = "idModule") Integer moduleId){
        try{
            List<AnswerDto> answerDtoList = iAnswerService.findByMClassAndModule(classId, moduleId);
            if ( answerDtoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("answers", answerDtoList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListAnswerByQuestion/{idClass}/{idModule}/{idQuestion}", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListAnswerByQuestion(@PathVariable(value = "idClass") Integer classId,
                                                               @PathVariable(value = "idModule") Integer moduleId,
                                                               @PathVariable(value = "idQuestion") Integer questionId){
        try{
            List<AnswerDto> answerDtoList = iAnswerService.findByMClassAndModuleAndQuestion(classId, moduleId, questionId);
            if ( answerDtoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("answers", answerDtoList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer Not Found", exc);
        }
    }


    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> addAll(@RequestBody ArrayList<AnswerDto> answerDtos ){
        try{
            List<AnswerDto> answerDtoList = iAnswerService.addAll(answerDtos);
            if ( answerDtoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("answers", answerDtoList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer Not Found", exc);
        }
    }

}
