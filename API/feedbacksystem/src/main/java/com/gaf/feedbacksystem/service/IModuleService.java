package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.dto.ModuleDto;

import java.util.List;

public interface IModuleService {
    List<ModuleDto> findAll();
    ModuleDto findModuleByModuleID(ModuleDto moduleDTO);
    void save(ModuleDto moduleDTO);
    void update(ModuleDto moduleDTO);
}
