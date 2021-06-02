package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.AnswerDto;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.TraineeCommentDto;
import com.gaf.feedbacksystem.service.IClassService;
import com.gaf.feedbacksystem.service.ICommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/comment")
@Tag(name = "comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PostMapping(value = "/")
    public TraineeCommentDto create(@Valid @RequestBody TraineeCommentDto traineeCommentDto){
        try{
            return  commentService.save(traineeCommentDto);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.TRAINEE_ROLE + "\")")
    @GetMapping(value = "/loadListCommentByTrainee/{idClass}/{idModule}", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListCommentByTrainee(@PathVariable(value = "idClass") Integer classId,
                                                                @PathVariable(value = "idModule") Integer moduleId){
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            Map result = new HashMap();
            List<TraineeCommentDto> traineeCommentDtos = commentService.findByMClassAndModuleAndTrainee(classId, moduleId,userDetails.getUsername());
            if ( traineeCommentDtos.isEmpty()) {
                result.put("added", 0);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            result.put("added", 1);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment Not Found", exc);
        }
    }
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListComment/{idClass}/{idModule}", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListComment(@PathVariable(value = "idClass") Integer classId,
                                                               @PathVariable(value = "idModule") Integer moduleId){
        try{
            List<TraineeCommentDto> traineeCommentDtos = commentService.findByMClassAndModule(classId, moduleId);
            if ( traineeCommentDtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("comments", traineeCommentDtos);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment Not Found", exc);
        }
    }
}