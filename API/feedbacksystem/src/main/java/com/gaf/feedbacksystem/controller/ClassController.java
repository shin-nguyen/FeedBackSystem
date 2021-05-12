package com.gaf.feedbacksystem.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.service.IClassService;
import com.gaf.feedbacksystem.service.IModuleService;

@RestController
@RequestMapping(value = "/class")
public class ClassController {
    @Autowired
    private IClassService classService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListClass", produces = "application/json")
    public ResponseEntity<Map<String,Iterable<?>>> getListClass(){
    	
    	Iterable<ClassDto> classList = classService.findAll();
//    	  if (((CharSequence) classList).isEmpty()) {
//    	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    	  }
    	  
    	  Map result = new HashMap();
    	  result.put("classes", classList);
    	  return new ResponseEntity<>(result, HttpStatus.OK);
//    	try {
//			Iterable<ClassDto> classList = classService.findAll();
//			return new ResponseEntity<Iterable<?>>(classList, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<Iterable<?>>(HttpStatus.BAD_REQUEST);
//		}
   
    }
//
//    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
//    @PostMapping(value = "/addModuleAdmin")
//    public boolean createModuleAdmin(@RequestBody ModuleDto moduleDTO){
//        try{
//            moduleService.save(moduleDTO);
//            return true;
//        }
//        catch (Exception exception){
//        }
//        return  false;
//    }
//
//
//    @PutMapping(value = "/updateModule")
//    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
//    public boolean updateModuleAdmin(@RequestBody ModuleDto moduleDTO){
//
//        try{
//                moduleService.update(moduleDTO);
//                return true;
//        }
//        catch (Exception exception){
//
//        }
//        return  false;
//    }
//    @DeleteMapping(value = "/deleteModule/{moduleId}")
//    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
//    public boolean deleteModuleAdmin(@RequestParam (name = "moduleId", required = true) Integer moduleId){
//        return  false;
//    }


}
