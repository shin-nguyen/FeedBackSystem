package com.gaf.feedbacksystem.service.impl;

import java.util.List;

import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Assignment;
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
	public List<ClassDto> findAll() {
		 List<Class> clazz = classRepository.findAllByDeletedIsFalse();
	     List<ClassDto> classDtos = ObjectMapperUtils.mapAll(clazz,ClassDto.class);		 
	     return classDtos;
	}

	@Override
	public List<ClassDto> findAllByTrainer(String userName) {
		List<Class> clazz = classRepository.findAllByTrainer(userName);

		List<ClassDto> classDtos = ObjectMapperUtils.mapAll(clazz,ClassDto.class);

		return classDtos;
	}

	@Override
	public ClassDto findById(Integer classId) {
		Class mClass = classRepository.findByClassID(classId);
		ClassDto classDto = ObjectMapperUtils.map(mClass,ClassDto.class);
		return classDto;
	}

	@Override
	public ClassDto update(ClassDto classDto) {
		Class oldClass=  classRepository.findByClassID(classDto.getClassID());

		oldClass.setClassName(classDto.getClassName());
		oldClass.setCapacity(classDto.getCapacity());

		return ObjectMapperUtils.map(classRepository.save(oldClass), ClassDto.class);
	}

	@Override
	public ClassDto save(ClassDto classDto) {
		Class  mClass = ObjectMapperUtils.map(classDto,Class.class);

		return ObjectMapperUtils.map(classRepository.save(mClass),ClassDto.class);
	}

	@Override
	public void deleteById(Integer id) {
		classRepository.deleteByClassId(id);
	}
}
