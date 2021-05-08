package com.gaf.feedbacksystem.service.impl;


import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.entity.Module;
import com.gaf.feedbacksystem.repository.ModuleRepository;
import com.gaf.feedbacksystem.service.IModuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ModuleDto> findAll() {
        List<Module> modules = moduleRepository.findAll();
        List<ModuleDto> moduleDTOS = mapper.map(modules, (Type) ModuleDto.class);
        return moduleDTOS;
    }

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
}
