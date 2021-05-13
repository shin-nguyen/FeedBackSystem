package com.gaf.feedbacksystem.service.impl;


import java.lang.reflect.Type;
import java.util.List;

import javax.transaction.Transactional;

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

    @Autowired
    private ModelMapper mapper;

  

    @Override
    public ModuleDto findModuleByModuleID(ModuleDto moduleDTO) {
        return null;
    }

    @Override
    public void save(ModuleDto moduleDTO) {

    }

    @Override
    public void update(ModuleDto moduleDTO) {
        Module oldModule=  moduleRepository.findModuleByModuleID(moduleDTO.getModuleID());


        oldModule.setModuleName(moduleDTO.getModuleName());

        moduleRepository.save(oldModule);
    }

	@Override
	public Iterable<ModuleDto> findAll() {
		 List<Module> modules = moduleRepository.findAll();
//	        List<ModuleDto> moduleDTOS = ObjectMapperUtils.mapAll(modules,ModuleDto.class);
		 
		 Type listType = new TypeToken<List<ModuleDto>>(){}.getType();
		 List<ModuleDto> moduleDTOS = mapper.map(modules,listType);
		 
		 
	        return moduleDTOS;
	}
}
