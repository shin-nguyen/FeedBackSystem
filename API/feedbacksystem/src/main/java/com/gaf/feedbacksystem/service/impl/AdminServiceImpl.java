package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.entity.Admin;

import com.gaf.feedbacksystem.repository.AdminRepository;
import com.gaf.feedbacksystem.service.IAdminService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminDto findByUserName(String userName) {
        Admin admin= adminRepository.findByUserName(userName);
        AdminDto adminDTO = ObjectMapperUtils.map(admin,AdminDto.class);

        return adminDTO;
    }

    @Override
    public AdminDto update(AdminDto admin) {
        Admin oldAdmin=  adminRepository.findByUserName(admin.getUserName());

        oldAdmin.setName(admin.getName());
        oldAdmin.setEmail(admin.getEmail());

        return ObjectMapperUtils.map(adminRepository.save(oldAdmin),AdminDto.class);
    }


    public List<AdminDto> findAll(){
        List<Admin> admins= adminRepository.findAll();
        List<AdminDto>  adminDto = ObjectMapperUtils.mapAll(admins,AdminDto.class);

        return adminDto;
    }

    @Override
    public AdminDto save(AdminDto adminDTO) {
        Admin admin = ObjectMapperUtils.map(adminDTO,Admin.class);

        return ObjectMapperUtils.map(adminRepository.save(admin),AdminDto.class);
    }
}
