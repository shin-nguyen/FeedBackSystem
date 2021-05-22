package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.FeedbackDto;
import com.gaf.feedbacksystem.service.IClassService;
import com.gaf.feedbacksystem.service.IFeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/feedback")
@Tag(name = "feedback")
public class FeedbackController {
    @Autowired
    private IFeedbackService feedbackService;
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/getListFeedback", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> getListFeedback(){
        try{
            List<FeedbackDto> feedbackList = feedbackService.findAll();
            if ( feedbackList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("feedbacks", feedbackList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public Map<String, Boolean> delete(@PathVariable(name = "id") Integer id){
        try {
            Map<String, Boolean> response = new HashMap<>();
            try {
                feedbackService.deleteById(id);
                response.put("deleted", Boolean.TRUE);

            } catch (Exception exception) {
                response.put("deleted", Boolean.FALSE);
            }
            return response;
        }  catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }

    }
}
