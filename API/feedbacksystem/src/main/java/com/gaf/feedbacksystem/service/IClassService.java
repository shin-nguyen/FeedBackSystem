package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.ClassDto;

import java.util.List;

public interface IClassService {
	List<ClassDto> findAll();
	ClassDto findById(Integer classId);
	ClassDto update(ClassDto classDto);
	ClassDto save(ClassDto classDto);
	void deleteById(String id);
}
