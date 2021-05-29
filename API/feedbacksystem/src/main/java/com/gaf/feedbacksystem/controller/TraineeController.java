package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.TraineeDto;
import com.gaf.feedbacksystem.service.ITraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/trainee")
public class TraineeController {

    @Autowired
    private ITraineeService traineeService;
	
	@PreAuthorize("hasRole(\"" + SystemConstant.TRAINEE_ROLE + "\")")
    @PostMapping(value = "/loadProfile")
    public ResponseEntity<TraineeDto> getTrainee(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        final TraineeDto traineeDto = traineeService.findByUserName(userDetails.getUsername());
        return  ResponseEntity.ok().body(traineeDto);
    }
}
