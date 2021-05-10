package com.gaf.feedbacksystem.service;


import java.util.List;

import com.gaf.feedbacksystem.dto.ModuleDto;

public interface IModuleService {
    List<ModuleDto> findAll();
    ModuleDto findModuleByModuleID(ModuleDto moduleDTO);
    void save(ModuleDto moduleDTO);
    void update(ModuleDto moduleDTO);
}
