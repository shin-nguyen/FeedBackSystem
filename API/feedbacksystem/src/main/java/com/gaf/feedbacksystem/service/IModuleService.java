package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.entity.Module;
import java.util.List;

public interface IModuleService {
    List<Module> findAll();
    ModuleDto findModuleByModuleID(ModuleDto moduleDTO);
    void save(ModuleDto moduleDTO);
    void update(ModuleDto moduleDTO);
}
