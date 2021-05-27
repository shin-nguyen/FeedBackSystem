package com.gaf.feedbacksystem.details;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.user.BaseUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class BaseUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private BaseUser baseUser;


    public BaseUserDetails(BaseUser baseUser){
        this.baseUser = baseUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role((SystemConstant.ADMIN_ROLE)));
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        }
//        return authorities;

        return Collections.singleton(new SimpleGrantedAuthority(SystemConstant.USER));
    }

    @Override
    public String getPassword() {
        return baseUser.getPassword();
    }

    @Override
    public String getUsername() {
        return baseUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
