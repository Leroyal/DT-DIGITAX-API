package com.digitax.controller;


import com.digitax.payload.ApiRes;
import com.digitax.security.jwt.JwtUtils;
import com.digitax.security.jwt.UserSession;
import com.digitax.constants.ResponseConstants;
import com.digitax.model.User;
import com.digitax.model.UserProfile;
import com.digitax.payload.ApiRes;
import com.digitax.payload.response.JwtResponse;
import com.digitax.payload.response.UserDetailsResponse;
import com.digitax.repository.UserProfileRepository;
import com.digitax.repository.UserRepository;
import com.digitax.security.jwt.JwtUtils;
import com.digitax.security.jwt.UserSession;
import com.digitax.service.UserProfileService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {


    @Autowired
    JwtUtils jwtUtils;
    

    @Autowired
    UserProfileRepository userProfileRepository;
    
    
    @Autowired
    UserProfileService userProfileService;
    
    @Autowired
    UserDetailsService userDetailsService;
    
    
    @Autowired
    UserRepository userRepository;
  


    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }


  
    @SuppressWarnings("unchecked")
	@GetMapping("/user-details")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> userProfileDetails() {
    	Optional<UserProfile> detailsObj = userProfileRepository.findByUserId(UserSession.getUserId());
    	Optional<User> user = userRepository.findById(UserSession.getUserId());
    	
    	UserDetailsResponse obj = new UserDetailsResponse(user, detailsObj);
        JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        System.out.println(UserSession.getUserId()); 
        return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);

    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/signout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", 200);
        statusObj.put("message", "SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));
    }
}
