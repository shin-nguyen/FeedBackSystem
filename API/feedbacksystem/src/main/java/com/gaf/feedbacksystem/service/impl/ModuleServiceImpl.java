package com.gaf.feedbacksystem.service.impl;


import java.lang.reflect.Type;
import java.util.List;

import javax.transaction.Transactional;

import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.entity.Module;
import com.gaf.feedbacksystem.repository.ModuleRepository;
import com.gaf.feedbacksystem.service.IModuleService;

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
//        Module oldModule=  moduleRepository.findModuleByModuleID(moduleDTO.getModuleID());
//        oldModule.setModuleName(moduleDTO.getModuleName());
//        return  moduleRepository.save(oldModule);
        return  null;
    }

    @Override
    public void deleteById(Integer id) {
        moduleRepository.deleteByModuleId(id);
    }


    /*
    Loi tut ***
     */
    @Override
    public List<ModuleDto> findAllByTrainee(String userName) {
        List<Module> modules = moduleRepository.findAllByTrainer(userName);
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
