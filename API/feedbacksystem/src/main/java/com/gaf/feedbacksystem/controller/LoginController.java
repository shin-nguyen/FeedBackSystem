package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.authentication.AuthenticationRequest;
import com.gaf.feedbacksystem.authentication.AuthenticationResponse;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.service.impl.UserDetailsServiceImpl;
import com.gaf.feedbacksystem.user.AdminUser;
import com.gaf.feedbacksystem.user.TraineeUser;
import com.gaf.feedbacksystem.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.function.Predicate;

@Transactional
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager ;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userAdminDetailsService ;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String getTest(
//            @PathVariable String username
    ){
        return "TangYuCheng";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest
                                                       )
            throws Exception {
        String role =authenticationRequest.getRole();
        Predicate<String> checkRole = x->{
            if (x.equals(SystemConstant.ADMIN_ROLE)||
                x.equals(SystemConstant.TRAINEE_ROLE)||
                x.equals(SystemConstant.TRAINER_ROLE)){
                SystemConstant.USER = role;
                return true;
            }
            return false;
        };

        if (!checkRole.test(role)){
            throw new Exception("Incorrect ROLE");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userAdminDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
