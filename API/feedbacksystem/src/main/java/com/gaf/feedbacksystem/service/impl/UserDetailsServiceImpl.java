package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.details.BaseUserDetails;
import com.gaf.feedbacksystem.repository.AdminRepository;
import com.gaf.feedbacksystem.repository.TraineeRepository;
import com.gaf.feedbacksystem.repository.TrainerRepository;
import com.gaf.feedbacksystem.user.BaseUser;
import com.gaf.feedbacksystem.user.UserFactory;
import com.gaf.feedbacksystem.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private  BaseUser baseUser;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        baseUser = UserFactory.getUser(SystemConstant.USER);

        switch (SystemConstant.USER) {

            case SystemConstant
                    .ADMIN_ROLE:
                baseUser.setUser(adminRepository.findByUserName(username));
                break;
            case SystemConstant
                    .TRAINEE_ROLE:
                baseUser.setUser(traineeRepository.findByUserName(username));
                break;
            case SystemConstant
                    .TRAINER_ROLE:
                baseUser.setUser(trainerRepository.findByUserName(username));
                break;

        }
        if (CommonUtils.isEmpty(baseUser)){
            throw new UsernameNotFoundException("Could not find user");
        }

        return new BaseUserDetails(baseUser);
    }

}
