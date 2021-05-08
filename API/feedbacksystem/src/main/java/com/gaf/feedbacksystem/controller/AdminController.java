package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.service.IAdminService;

import com.gaf.feedbacksystem.service.impl.AdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/admin")
@Tag(name = "admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Operation(description = "Thao tac Admin", responses = {
            @ApiResponse(
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AdminDto.class))), responseCode = "200") })
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "Thành công"),
            @ApiResponse(responseCode  = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode  = "403", description = "Truy cập bị cấm"),
            @ApiResponse(responseCode  = "404", description = "Không tìm thấy")
    })

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @PostMapping(value = "/loadProfile")
    public ResponseEntity<AdminDto> getAdmin(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return  ResponseEntity.ok().body(adminService.findByUserName(userDetails.getUsername()));
    }


    @PutMapping(value = "/updateProfile")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public boolean updateAdmin(
            @Valid
            @Parameter(description = "Update Admin", required = true, schema = @Schema(implementation = Admin.class))
            @RequestBody AdminDto adminDTO){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();


        try{
            if (adminDTO.getUserName().equals(userDetails.getUsername())){
                adminService.update(adminDTO);
                return true;
            }
        }
        catch (Exception exception){

        }
        return  false;
    }
}
