package com.gaf.feedbacksystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
