package com.gaf.feedbacksystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.gaf.feedbacksystem.dto.FeedbackDto;
import com.gaf.feedbacksystem.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gaf.feedbacksystem.MyResourceNotFoundException;
import com.gaf.feedbacksystem.constant.SystemConstant;
import com.gaf.feedbacksystem.service.IFeedbackService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/feedback")
@Tag(name = "feedback")
public class FeedbackController {
	
	@Autowired
	private IFeedbackService feedbackService;
	@Autowired
	private IAdminService adminService;

	 @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
	    @GetMapping("/feedbacks/{id}")
	    public ResponseEntity<FeedbackDto> getFeedback(@PathVariable(value = "id") Integer feedbackID) {
	        try{
	            final FeedbackDto feedbackDto = feedbackService.findById(feedbackID);
	            if (feedbackDto==null){
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return ResponseEntity.ok().body(feedbackDto);
	        }
	        catch (MyResourceNotFoundException exc) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feedbacks Not Found", exc);
	        }
	    }

	@PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
	@GetMapping(value = "/getListFeedback", produces = "application/json")
	public ResponseEntity<Map<String, List<?>>> getListFeedback(){
		try{
			List<FeedbackDto> feedbackList = feedbackService.findAll();
			if ( feedbackList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			Map result = new HashMap();
			result.put("feedbacks", feedbackList);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch (MyResourceNotFoundException exc) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classes Not Found", exc);
		}
	}
	 
	 @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
	    @PostMapping(value = "/")
	    public FeedbackDto create(@Valid @RequestBody FeedbackDto feedbackDto){
	        try{
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();

	        	feedbackDto.setAdmin(adminService.findByUserName(userDetails.getUsername()));
	            return  feedbackService.save(feedbackDto);
	        }
	        catch (MyResourceNotFoundException exc) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feedbacks Not Found", exc);
	        }
	    }
	 
	 @PutMapping(value = "/")
	    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
	    public ResponseEntity<FeedbackDto> update(@Valid  @RequestBody FeedbackDto feedbackDto){
	        try {
	            final FeedbackDto updatedFeedback = feedbackService.update(feedbackDto);

	            return ResponseEntity.ok(updatedFeedback);
	        }
	        catch (MyResourceNotFoundException exc) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feedbacks Not Found", exc);
	        }
	    }
	 
	 @DeleteMapping(value = "/{id}")
	    @PreAuthorize("hasRole(\"" + SystemConstant.ADMIN_ROLE + "\")")
	    public Map<String, Boolean> delete(@PathVariable (name = "id") Integer id){
	        try {
	            Map<String, Boolean> response = new HashMap<>();
	            try {
	                feedbackService.deleteById(id);
	                response.put("deleted", Boolean.TRUE);

	            } catch (Exception exception) {
	                response.put("deleted", Boolean.FALSE);
	            }
	            return response;
	        }  catch (MyResourceNotFoundException exc) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feedbacks Not Found", exc);
	        }

	    }
}
