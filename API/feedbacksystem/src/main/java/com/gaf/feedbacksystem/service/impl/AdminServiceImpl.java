package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.entity.Admin;

import com.gaf.feedbacksystem.repository.AdminRepository;
import com.gaf.feedbacksystem.service.IAdminService;
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
    private  ModelMapper mapper;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminDto findByUserName(String userName) {
        Admin admin= adminRepository.findByUserName(userName);
        AdminDto adminDTO = mapper.map(admin, (Type) AdminDto.class);
        return adminDTO;
    }

    @Override
    public void update(AdminDto admin) {
        Admin oldAdmin=  adminRepository.findByUserName(admin.getUserName());

        oldAdmin.setName(admin.getName());
        oldAdmin.setEmail(admin.getEmail());
        adminRepository.save(oldAdmin);
    }


    public List<AdminDto> findAll(){
        List<Admin> admins= adminRepository.findAll();
        List<AdminDto>  adminDto = mapper.map(admins, (Type) AdminDto.class);
        return adminDto;
    }

    @Override
    public void save(AdminDto adminDTO) {
        Admin admin = mapper.map(adminDTO,Admin.class);
        adminRepository.save(admin);
    }



}
