package com.gaf.feedbacksystem.service;



import com.gaf.feedbacksystem.dto.AdminDto;

import java.util.List;

public interface IAdminService {
    List<AdminDto> findAll();
    AdminDto save(AdminDto admin);
    AdminDto findByUserName(String userName);
    AdminDto update(AdminDto admin);
}
