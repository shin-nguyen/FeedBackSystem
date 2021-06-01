package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.Feedback;
import com.gaf.feedbacksystem.entity.Module;
import com.gaf.feedbacksystem.repository.ModuleRepository;
import com.gaf.feedbacksystem.service.IModuleService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    ModuleRepository moduleRepository;

    @Override
    public ModuleDto save(ModuleDto moduleDTO) {
        Module mModule = ObjectMapperUtils.map(moduleDTO,Module.class);

        return ObjectMapperUtils.map(moduleRepository.save(mModule), ModuleDto.class);
    }

    @Override
    public ModuleDto update(ModuleDto moduleDTO) {
        Module oldModule=  moduleRepository.findByModuleID(moduleDTO.getModuleID());

        oldModule.setModuleName(moduleDTO.getModuleName());
        oldModule.setAdmin(ObjectMapperUtils.map(moduleDTO.getAdmin(), Admin.class));
        oldModule.setFeedback(ObjectMapperUtils.map(moduleDTO.getFeedback(), Feedback.class));
        oldModule.setStartTime(moduleDTO.getStartTime());
        oldModule.setEndTime(moduleDTO.getEndTime());
        oldModule.setFeedbackStartTime(moduleDTO.getFeedbackStartTime());
        oldModule.setFeedbackEndTime(moduleDTO.getFeedbackEndTime());

        return ObjectMapperUtils.map(moduleRepository.save(oldModule), ModuleDto.class);
    }

    @Override
    public void deleteById(Integer id) {
        moduleRepository.deleteByModuleId(id);
    }

    @Override
    public List<ModuleDto> findAllByTrainee(String userName) {
        List<Module> modules = moduleRepository.findAllByTrainee(userName);
        List<ModuleDto> moduleDtos = ObjectMapperUtils.mapAll(modules,ModuleDto.class);
        return moduleDtos;
    }


    @Override
    public List<ModuleDto> findAllByTrainer(String userName) {
        List<Module> modules = moduleRepository.findAllByTrainer(userName);
        List<ModuleDto> moduleDtos = ObjectMapperUtils.mapAll(modules,ModuleDto.class);
        return moduleDtos;
    }


    @Override
	public List<ModuleDto> findAll() {
		 List<Module> modules = moduleRepository.findAllByDeletedIsFalse();
		 List<ModuleDto> moduleDTOS = ObjectMapperUtils.mapAll(modules,ModuleDto.class);
		 
		 return moduleDTOS;
	}
}
