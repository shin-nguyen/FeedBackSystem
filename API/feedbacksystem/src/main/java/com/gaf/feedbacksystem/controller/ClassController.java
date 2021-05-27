package com.gaf.feedbacksystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.dto.ClassDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.service.IClassService;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/class")
@Tag(name = "class")
public class ClassController {
    @Autowired
    private IClassService classService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping("/classes/{id}")
    public ResponseEntity<ClassDto> getClass(@PathVariable(value = "id") Integer classID) {
        try{
            final ClassDto classDto = classService.findById(classID);
            if (classDto==null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok().body(classDto);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListClass", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> getListClass(){
        try{
    	    List<ClassDto> classList = classService.findAll();
    	        if ( classList.isEmpty()) {
    	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	        }
    	    Map result = new HashMap();
    	    result.put("classes", classList);
    	    return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.TRAINER_ROLE + "\")")
    @GetMapping(value = "/loadListClassByTrainer", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListClassByTrainer(){
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userName = userDetails.getUsername();

            List<ClassDto> classList = classService.findAllByTrainer(userName);
            if ( classList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("classes", classList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListClassByTrainee", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListClassByTrainee(){
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userName = userDetails.getUsername();

            List<ClassDto> classList = classService.findAllByTrainee(userName);
            if ( classList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("classes", classList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }


    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PostMapping(value = "/")
    public ClassDto create(@Valid @RequestBody ClassDto classDto){
        try{
            return  classService.save(classDto);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }


    @PutMapping(value = "/")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public ResponseEntity<ClassDto> update(@Valid  @RequestBody ClassDto classDto){
        try {
            final ClassDto updatedEmployee = classService.update(classDto);

            return ResponseEntity.ok(updatedEmployee);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public Map<String, Boolean> delete(@PathVariable (name = "id") Integer id){
        try {
            Map<String, Boolean> response = new HashMap<>();
            try {
                classService.deleteById(id);
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
