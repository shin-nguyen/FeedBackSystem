package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.*;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Trainer;
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

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/assignment")
public class AssignmentController {

    @Autowired
    private IAssignmentService assignmentService;
    @Autowired
    private  ITrainerService trainerService;

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
    @PostMapping(value = "/")
    public AssignmentDto create(@RequestBody AssignmentDto assignmentDto){
        try{
            return  assignmentService.save(assignmentDto);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignment Not Found", exc);
        }
    }

    @PutMapping(value = "/{userName}")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public ResponseEntity<AssignmentDto> update(@Valid @PathVariable(name = "userName") String userName,@Valid @RequestBody AssignmentDto newAssignment){
//        try {
            TrainerDto trainer = trainerService.findByUserName(userName);
            if (trainer==null)
                throw new MyResourceNotFoundException();

            final AssignmentDto assignment = assignmentService.update(userName,newAssignment);

            return ResponseEntity.ok(assignment);
//        }
//        catch (MyResourceNotFoundException exc) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
//        }
    }
    @DeleteMapping(value = "/{idClass}/{idModule}/{userName}")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public Map<String, Boolean> delete(@PathVariable (name = "idClass") Integer idClass,
                                       @PathVariable (name = "idModule") Integer idModule,
                                       @PathVariable(name="userName") String userName){
        try {
            Map<String, Boolean> response = new HashMap<>();
            try {
                assignmentService.deleteById(idClass,idModule,userName);
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
