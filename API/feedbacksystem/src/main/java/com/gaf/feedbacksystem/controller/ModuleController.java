package com.gaf.feedbacksystem.controller;

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

@RestController
@RequestMapping(value = "/module")
public class ModuleController {
    @Autowired
    private IModuleService moduleService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadModuleAdmin")
    public ResponseEntity<Iterable<ModuleDto>> getListModuleAdmin(){
    	try {
			Iterable<ModuleDto> moduleList = moduleService.findAll();
			return new ResponseEntity<Iterable<ModuleDto>>(moduleList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<ModuleDto>>(HttpStatus.BAD_REQUEST);
		}
   
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PostMapping(value = "/addModuleAdmin")
    public boolean createModuleAdmin(@RequestBody ModuleDto moduleDTO){
        try{
            moduleService.save(moduleDTO);
            return true;
        }
        catch (Exception exception){
        }
        return  false;
    }


    @PutMapping(value = "/updateModule")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public boolean updateModuleAdmin(@RequestBody ModuleDto moduleDTO){

        try{
                moduleService.update(moduleDTO);
                return true;
        }
        catch (Exception exception){

        }
        return  false;
    }
    @DeleteMapping(value = "/deleteModule/{moduleId}")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public boolean deleteModuleAdmin(@RequestParam (name = "moduleId", required = true) Integer moduleId){
        return  false;
    }


}
