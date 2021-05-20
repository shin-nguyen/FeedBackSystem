package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.dto.ClassDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.service.IModuleService;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/module")
public class ModuleController {
    @Autowired
    private IModuleService moduleService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListModule", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListModule(){
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
}
