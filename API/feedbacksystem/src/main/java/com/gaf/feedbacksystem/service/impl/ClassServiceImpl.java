package com.gaf.feedbacksystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.repository.ClazzRepository;
import com.gaf.feedbacksystem.service.IClassService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import com.gaf.feedbacksystem.entity.Class;

@Service
public class ClassServiceImpl implements IClassService  {

    @Autowired
    ClazzRepository classRepository;
    
	@Override
	public Iterable<ClassDto> findAll() {
		 List<Class> clazz = classRepository.findAll();
	     List<ClassDto> classDtos = ObjectMapperUtils.mapAll(clazz,ClassDto.class);		 
	     return classDtos;
	}
	

}
