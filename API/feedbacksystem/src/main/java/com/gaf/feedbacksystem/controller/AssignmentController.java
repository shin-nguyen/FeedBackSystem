package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.*;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.service.IAssignmentService;
import com.gaf.feedbacksystem.service.ITrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping(value = "/assignment")
public class AssignmentController {

    @Autowired
    private IAssignmentService assignmentService;


    @Operation(description = "Thao tac Assignment", responses = {
            @ApiResponse(
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AssignmentDto.class))), responseCode = "200") })
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "Thành công"),
            @ApiResponse(responseCode  = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode  = "403", description = "Truy cập bị cấm"),
            @ApiResponse(responseCode  = "404", description = "Không tìm thấy")
    })
    @PreAuthorize("hasAnyRole(\"" + SystemConstant.ADMIN_ROLE + "\"," +
                            "\"" + SystemConstant.TRAINER_ROLE + "\")")
    @GetMapping(value = "/loadListAssignment")
    public ResponseEntity<Map<String, List<?>>> getListAssignment(){
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            List<AssignmentDto> assignmentList = new ArrayList<>();

            if (userDetails.getAuthorities().contains(SystemConstant.TRAINER_ROLE)) {
                assignmentList =  assignmentService.findByTrainerUserName(userDetails.getUsername());
            } else{
                assignmentList = assignmentService.findAll();
            }
            if ( assignmentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("assignments", assignmentList);
            return ResponseEntity.ok().body(result);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignment Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PutMapping(value = "/updateAssignment/{id}")
    public ResponseEntity updateAssignmentByTrainer (@RequestBody AssignmentIdDto assignmentIdDto, @PathVariable Integer trainerId){


        return null;
    }
//    @DeleteMapping("/")
//    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
//    public ResponseEntity delete(@RequestBody AssignmentIdDto assignmentIdDto) {
////        return  ResponseEntity.ok().body(assignmentService.deleteById(assignmentIdDto););
//    }

}
