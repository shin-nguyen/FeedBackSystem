package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.TraineeAssignmentDto;
import com.gaf.feedbacksystem.service.ITraineeAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/traineeAssignment")
public class TraineeAssignmentController {

    @Autowired
    private ITraineeAssignmentService traineeAssignmentService;

    @PreAuthorize("hasRole(\"" + SystemConstant.TRAINEE_ROLE + "\")")
    @PostMapping(value = "/")
    public TraineeAssignmentDto create(@RequestBody TraineeAssignmentDto traineeAssignmentDto){
        try{
            return  traineeAssignmentService.save(traineeAssignmentDto);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trainee_Assignment Not Found", exc);
        }
    }
}
