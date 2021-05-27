package com.gaf.feedbacksystem.controller;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.service.IAdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        final AdminDto  adminDto = adminService.findByUserName(userDetails.getUsername());
        return  ResponseEntity.ok().body(adminDto);
    }

    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    @GetMapping(value = "/loadListAdmin", produces = "application/json")
    public ResponseEntity<Map<String, List<?>>> loadListAdmin(){
        try{
            List<AdminDto> adminList = adminService.findAll();
            if ( adminList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Map result = new HashMap();
            result.put("admins", adminList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin Not Found", exc);
        }
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
    public ResponseEntity<AdminDto> update(
            @Valid
            @Parameter(description = "Update Admin", required = true, schema = @Schema(implementation = AdminDto.class))
            @RequestBody AdminDto adminDTO){
        try{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

            if (adminDTO.getUserName().equals(userDetails.getUsername())){
               final AdminDto updateAdmin =  adminService.update(adminDTO);
               return ResponseEntity.ok(updateAdmin);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (MyResourceNotFoundException exc){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
        }
    }
}
