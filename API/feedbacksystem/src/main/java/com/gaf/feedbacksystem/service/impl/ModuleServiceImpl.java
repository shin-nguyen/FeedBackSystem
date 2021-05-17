package com.gaf.feedbacksystem.service.impl;


import java.lang.reflect.Type;
import java.util.List;

import javax.transaction.Transactional;

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
        return  null;
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

    }


    @Override
	public List<ModuleDto> findAll() {
		 List<Module> modules = moduleRepository.findAll();
		 List<ModuleDto> moduleDTOS = ObjectMapperUtils.mapAll(modules,ModuleDto.class);
		 
		 return moduleDTOS;
	}
}
