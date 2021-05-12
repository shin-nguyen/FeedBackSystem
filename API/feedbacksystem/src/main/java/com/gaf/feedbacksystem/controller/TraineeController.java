package com.gaf.feedbacksystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.AdminDto;

@RestController
@RequestMapping(value = "/trainee")
public class TraineeController {
	
//	@PreAuthorize("hasRole(\"" + SystemConstant.TRAINEE_ROLE + "\")")
//    @PostMapping(value = "/loadProfile")
//    public ResponseEntity<AdminDto> getAdmin(){
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//
//        return  ResponseEntity.ok().body(adminService.findByUserName(userDetails.getUsername()));
//    }
}
