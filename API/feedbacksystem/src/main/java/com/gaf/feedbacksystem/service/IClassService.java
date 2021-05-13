package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.ClassDto;

public interface IClassService {
	Iterable<ClassDto> findAll();
}
