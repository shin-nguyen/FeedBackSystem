package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.TraineeAssignmentDto;
import com.gaf.feedbacksystem.dto.TraineeDto;
import com.gaf.feedbacksystem.service.IAssignmentService;
import com.gaf.feedbacksystem.service.ITraineeAssignmentService;
import com.gaf.feedbacksystem.service.ITraineeService;
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
    @Autowired
    private ITraineeService traineeService;
    @Autowired
    private IAssignmentService assignmentService;

//    @PreAuthorize("hasRole(\"" + SystemConstant.TRAINEE_ROLE + "\")")
//    @PostMapping(value = "/{userid}/{code}")
//    public TraineeAssignmentDto create(@PathVariable (name = "userid") String userid,
//                                       @PathVariable (name = "code") String code){
//        try{
//            AssignmentDto assignmentDto = assignmentService.findByCode(code);
//            TraineeDto traineeDto = traineeService.findByUserName(userid);
//
//            if (assignmentDto == null || traineeDto == null){
//                throw new MyResourceNotFoundException();
//            }
//
//            TraineeAssignmentDto traineeAssignmentDto = new TraineeAssignmentDto();
//            traineeAssignmentDto.setAssignment(assignmentDto);
//            traineeAssignmentDto.setTrainee(traineeDto);
//
//            return  traineeAssignmentService.save(traineeAssignmentDto);
//        }
//        catch (MyResourceNotFoundException exc) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trainee_Assignment Not Found", exc);
//        }
//    }

    @PostMapping(value = "/{username}/{code}")
    @PreAuthorize("hasRole(\"" + SystemConstant.TRAINEE_ROLE + "\")")
    public Map<String, Boolean> create(@PathVariable (name = "username") String username,
                                       @PathVariable (name = "code") String code) {
        try {
            Map<String, Boolean> response = new HashMap<>();
            AssignmentDto assignmentDto = assignmentService.findByCode(code);
            TraineeDto traineeDto = traineeService.findByUserName(username);

            if (traineeDto == null){
                throw new MyResourceNotFoundException();
            }

            if (assignmentDto == null){
                response.put("add", Boolean.FALSE);
            }
            else if (assignmentDto.getRegistrationCode().equals(code)){
                TraineeAssignmentDto traineeAssignmentDto = new TraineeAssignmentDto();
                traineeAssignmentDto.setAssignment(assignmentDto);
                traineeAssignmentDto.setTrainee(traineeDto);

                traineeAssignmentService.save(traineeAssignmentDto);

                response.put("add", Boolean.TRUE);
            }
//                else {
//                    response.put("add", Boolean.FALSE);
//                }

            return response;
        } catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }
}