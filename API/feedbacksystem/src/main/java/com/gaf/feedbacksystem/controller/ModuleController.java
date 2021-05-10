package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.entity.Module;
import com.gaf.feedbacksystem.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/module")
public class ModuleController {
    @Autowired
    private IModuleService moduleService;

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadModuleAdmin")
    public List<Module> getListModuleAdmin(){
        return moduleService.findAll();
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
