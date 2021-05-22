package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.TypeFeedbackDto;
import com.gaf.feedbacksystem.service.ITypeFeedback;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/typefeedback")
@Tag(name = "typefeedback")
public class TypeFeedbackController {
    @Autowired
    private ITypeFeedback typeFeedback;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListTypeFeedback", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListTypeFeedback(){
        try{
            List<TypeFeedbackDto> typeFeedbacks = typeFeedback.findAll();
            if ( typeFeedbacks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("typeFeedbacks", typeFeedbacks);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TypeFeedback Not Found", exc);
        }
    }
}