package com.gaf.feedbacksystem.controller;


import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.service.IModuleService;
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
@RequestMapping(value = "/module")
public class ModuleController {
    @Autowired
    private IModuleService moduleService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadModuleAdmin", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadModuleAdmin(){
        try{
            List<ModuleDto> modules = moduleService.findAll();
            if ( modules.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("modules", modules);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Module Not Found", exc);
        }
    }


    @PreAuthorize("hasRole(\"" + SystemConstant.TRAINER_ROLE + "\")")
    @GetMapping(value = "/loadModuleTrainer", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadModuleTrainer(){
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userName = userDetails.getUsername();

            List<ModuleDto> moduleList = moduleService.findAllByTrainer(userName);
            if ( moduleList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("modules", moduleList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadModuleTrainee", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadModuleTrainee(){
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userName = userDetails.getUsername();

            List<ModuleDto> moduleList = moduleService.findAllByTrainee(userName);
            if ( moduleList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("modules", moduleList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }


    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PostMapping(value = "/")
    public ModuleDto create(@Valid @RequestBody ModuleDto moduleteDto){
        try{
            return  moduleService.save(moduleteDto);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public Map<String, Boolean> delete(@PathVariable(name = "id") Integer id){
        try {
            Map<String, Boolean> response = new HashMap<>();
            try {
                moduleService.deleteById(id);
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
