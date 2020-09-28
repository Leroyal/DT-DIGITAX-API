package com.digitax.controller;

import io.swagger.annotations.Api;

import com.digitax.model.DeviceMetadata;
import com.digitax.model.ERole;
import com.digitax.model.MarketingPreference;
import com.digitax.model.Role;
import com.digitax.model.User;
import com.digitax.model.UserProfile;
import com.digitax.payload.ApiRes;
import com.digitax.payload.request.SigninRequest;
import com.digitax.payload.request.SignupRequest;
import com.digitax.payload.request.UpdatePasswordRequest;
import com.digitax.payload.response.JwtResponse;
import com.digitax.payload.response.SessionResponse;
import com.digitax.repository.DeviceMetadataRepository;
import com.digitax.repository.RoleRepository;
import com.digitax.repository.UserRepository;
import com.digitax.security.jwt.AuthEntryPointJwt;
import com.digitax.security.jwt.JwtUtils;
import com.digitax.security.jwt.UserSession;
import com.digitax.service.DeviceMetadataService;
import com.digitax.security.services.UserDetailsImpl;
import com.digitax.service.EmailService;
import com.digitax.service.SmsService;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.digitax.controller.constants.Errors.*;
import com.digitax.constants.ResponseConstants;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(tags = {"AuthController"}, description = "Auth controller")
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Value("${digitax.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    SmsService smsService;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired 
    EmailService sendGridEmailService;
    
    @Autowired 
    DeviceMetadataRepository deviceMetadataRepository;
    
    @Autowired 
    DeviceMetadataService devideMetadataService;
    
    
    /**##
     * 
     * @param loginRequest
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest loginRequest,HttpServletRequest request,BindingResult bindingResult) {
    	//smsService.sendSMS("+15005550006", "test");
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          }
    	    long jwtExpiry;
		   	  if(loginRequest.getDeviceType().equals("IOS") || loginRequest.getDeviceType().equals("ANDROID")) {
		         jwtExpiry = jwtExpirationMs*360;
		         System.out.println(jwtExpiry);
			   	}
			   	else
			   	{
			   	 jwtExpiry = jwtExpirationMs;
			   	}
			   	  
		   	  
    	try {
    		Authentication authentication = null;
    	        if (userRepository.existsByEmail(loginRequest.getUsername())) {
    	        	 User detailsObj = userRepository.findByEmail(loginRequest.getUsername());
    	        	 authentication = authenticationManager.authenticate(
       	                    new UsernamePasswordAuthenticationToken(detailsObj.getUsername().toLowerCase().trim(), loginRequest.getPassword()));
    	        }
    	        else if (userRepository.existsByPhone(loginRequest.getUsername())) {
    	        	 User  detailsObj = userRepository.findByPhone(loginRequest.getUsername());
    	        	authentication = authenticationManager.authenticate(
       	                    new UsernamePasswordAuthenticationToken(detailsObj.getUsername().toLowerCase().trim(), loginRequest.getPassword()));
    	        }
    	        else if (userRepository.existsByUsername(loginRequest.getUsername())) {
       			 authentication = authenticationManager.authenticate(
       	                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername().toLowerCase().trim(), loginRequest.getPassword()));
       	        }
	    		else {
	    			JSONObject statusObj = new JSONObject();
			        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
			        statusObj.put("message", "User Does not exists");
			        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
			       
	    		}
    	        
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication,loginRequest.getDeviceType());

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            Object Sessionobj = new SessionResponse(jwt, jwtExpiry);
            JSONObject userDetailsObj = new JSONObject();
            userDetailsObj.put("id", userDetails.getId());
            userDetailsObj.put("username", userDetails.getUsername());
            userDetailsObj.put("email", userDetails.getEmail());
            userDetailsObj.put("authorities", userDetails.getAuthorities());

            JwtResponse obj = new JwtResponse(Sessionobj, userDetailsObj);
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", 200);
            statusObj.put("message", "SUCCESS");
            
            devideMetadataService.saveUserActivity(request,UserSession.getUserId());
	        
            return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);
       } catch (Exception e) {
    	   JSONObject statusObj = new JSONObject();
           statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
           statusObj.put("message", "FAILURE");
           logger.error("Unauthorized error: {}");
           return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);
           }
    }

    /**##
     * 
     * @param signUpRequest
     * @return
     */
    @SuppressWarnings({"unchecked"})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,BindingResult bindingResult) {
    	
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        }
    	
    	long jwtExpiry;
	   	  if(signUpRequest.getDeviceType().equals("IOS") || signUpRequest.getDeviceType().equals("ANDROID")) {
	         jwtExpiry = jwtExpirationMs*360;
	         System.out.println(jwtExpiry);
	   	}
	   	else
	   	{
	   	 jwtExpiry = jwtExpirationMs;
	   	}
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", ResponseConstants.USER_ALREADY_EXISTS);
            statusObj.put("message", "Username is already taken!");
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));

        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", ResponseConstants.USER_ALREADY_EXISTS);
            statusObj.put("message", "Email is already in use");
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));

        }
        System.out.println(signUpRequest.getPhone());
        if (signUpRequest.getPhone() != null && userRepository.existsByPhone(signUpRequest.getPhone())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code",ResponseConstants.USER_ALREADY_EXISTS);
            statusObj.put("message", "Phone is already in use!");
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));

        }

    

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhone(),
                signUpRequest.getDeviceType(),
                System.currentTimeMillis());
        
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
       
        userRepository.save(user);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication,signUpRequest.getDeviceType());

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles1 = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

     
            Object Sessionobj = new SessionResponse(jwt,jwtExpiry);

            JSONObject userDetailsObj = new JSONObject();
            userDetailsObj.put("id", userDetails.getId());
            userDetailsObj.put("username", userDetails.getUsername());
            userDetailsObj.put("email", userDetails.getEmail());
            userDetailsObj.put("authorities", userDetails.getAuthorities());

            JwtResponse obj = new JwtResponse(Sessionobj, userDetailsObj);

            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", ResponseConstants.SUCCESS);
            statusObj.put("message", "SUCCESS");
            return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);
        } catch (Exception ex) {
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
            statusObj.put("message", "FAILURE");
            logger.error("Unauthorized error: {}");
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @PostMapping("/signout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    	JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
    }
    
    
    @SuppressWarnings("unchecked")
    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest request,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          }    
    	if (userRepository.existsByEmail(request.getEmail())) {
       	 User detailsObj = userRepository.findByEmail(request.getEmail());
       	Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(detailsObj.getUsername().toLowerCase().trim(), request.getOldPassword()));
       	detailsObj.setPassword(encoder.encode(request.getPassword()));
       	userRepository.save(detailsObj);
       	JSONObject statusObj = new JSONObject();	   
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
    	}
    	else {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
	        statusObj.put("message", "User Does not exists");
	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
	       
		}
    		    
        
    }
    
    
    @SuppressWarnings("unchecked")
	@GetMapping("/user-accoun-activity")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserAccountActivity() {
		try {
		List<DeviceMetadata> detailsObj = deviceMetadataRepository.findByUserId(UserSession.getUserId());
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(detailsObj, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
}