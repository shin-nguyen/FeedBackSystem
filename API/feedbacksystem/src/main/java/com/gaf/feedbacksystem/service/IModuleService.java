package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.dto.ModuleDto;

import java.util.List;

public interface IModuleService {
	List<ModuleDto> findAll();
    ModuleDto save(ModuleDto moduleDTO);
    ModuleDto update(ModuleDto moduleDTO);
    void deleteById(Integer id);
    List<ModuleDto> findAllByTrainee(String userName);
    List<ModuleDto> findAllByTrainer(String userName);
}
