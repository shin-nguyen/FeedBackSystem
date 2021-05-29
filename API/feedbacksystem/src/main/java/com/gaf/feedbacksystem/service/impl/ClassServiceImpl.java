package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.TraineeDto;
import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.repository.ClazzRepository;
import com.gaf.feedbacksystem.repository.TraineeRepository;
import com.gaf.feedbacksystem.service.IClassService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassServiceImpl implements IClassService {

    @Autowired
	ClazzRepository classRepository;

    @Autowired
	TraineeRepository traineeRepository;
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
	public List<ClassDto> findAllByTrainee(String userName) {
		List<Class> clazz = classRepository.findAllByTrainee(userName);

		List<ClassDto> classDtos = ObjectMapperUtils.mapAll(clazz,ClassDto.class);

		return classDtos;	}

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
	@Transactional
	public ClassDto updateTrainee(Integer oldIdClass, Integer newIdClass, TraineeDto traineeDto) {
		Class oldClass=  classRepository.findByClassID(oldIdClass);
		Class newClass =  classRepository.findByClassID(newIdClass);
		Trainee trainee = traineeRepository.findByUserName(traineeDto.getUserName());

		ClassDto oldClassDto=deleteTrainee(traineeDto.getUserName(),
				ObjectMapperUtils.map(oldClass, ClassDto.class));

		newClass.addTrainee(trainee);


		return ObjectMapperUtils.map(classRepository.save(newClass), ClassDto.class);
	}

	@Override
	public ClassDto deleteTrainee(String id,ClassDto classDto) {
		Class oldClass=  classRepository.findByClassID(classDto.getClassID());

		Trainee trainee = 	traineeRepository.findByUserName(id);
		oldClass.removeTrainee(trainee);

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
