package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.TraineeAssignmentDto;
import com.gaf.feedbacksystem.dto.TraineeDto;
import com.gaf.feedbacksystem.entity.TraineeAssignment;
import com.gaf.feedbacksystem.service.IAssignmentService;
import com.gaf.feedbacksystem.service.IClassService;
import com.gaf.feedbacksystem.service.ITraineeAssignmentService;
import com.gaf.feedbacksystem.service.ITraineeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.BasicPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/traineeAssignment")
public class TraineeAssignmentController {

    @Autowired
    private ITraineeAssignmentService traineeAssignmentService;
    @Autowired
    private ITraineeService traineeService;
    @Autowired
    private IAssignmentService assignmentService;
    @Autowired
    private IClassService classService;

    @PostMapping(value = "/{username}/{code}")
    @PreAuthorize("hasRole(\"" + SystemConstant.TRAINEE_ROLE + "\")")
    public Map<String, Integer> create(@PathVariable (name = "username") String username,
                                        @PathVariable (name = "code") String code) {
        try {
            Map<String, Integer> response = new HashMap<>();
                AssignmentDto assignmentDto = assignmentService.findByCode(code);
                TraineeDto traineeDto = traineeService.findByUserName(username);
            if (traineeDto == null){
                throw new MyResourceNotFoundException();
            }

//            TraineeAssignmentDto traineeAssignmentDto = traineeAssignmentService.checkIsAvailable(username, code);
                try {
                    //check have trainee joined class
//                    if (traineeAssignmentDto != null){
//                        response.put("added", 0);
//                    }else

                    //if  is null, it's mean invalid code
                    if (assignmentDto == null){
                        response.put("added", 1);
                    }
                    //code is available in assignment, so add to trainee_assignment
                    else if (assignmentDto.getRegistrationCode().equals(code)){
                        ClassDto classDto = classService.findById(assignmentDto.getmClass().getClassID());

                        TraineeAssignmentDto traineeAssignmentDto = new TraineeAssignmentDto();
                        traineeAssignmentDto.setAssignment(assignmentDto);
                        traineeAssignmentDto.setTrainee(traineeDto);

                        classService.addEnrollment(traineeDto.getUserName(), classDto.getClassID());
                        traineeAssignmentService.save(traineeAssignmentDto);

                        response.put("added", 2);
                    }
                }catch (MyResourceNotFoundException exc){
                    response.put("added", 3);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error", exc);
                }
            return response;
        } catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }
}