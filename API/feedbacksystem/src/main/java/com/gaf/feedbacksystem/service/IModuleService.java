package com.gaf.feedbacksystem.service;


import java.util.List;

import com.gaf.feedbacksystem.dto.ModuleDto;

public interface IModuleService {
	List<ModuleDto> findAll();
    ModuleDto save(ModuleDto moduleDTO);
    ModuleDto update(ModuleDto moduleDTO);
    void deleteById(Integer id);
}
