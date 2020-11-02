package com.digitax.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;

import com.digitax.model.DeviceMetadata;
import com.digitax.model.ERole;
import com.digitax.model.MarketingPreference;
import com.digitax.model.Role;
import com.digitax.model.User;
import com.digitax.model.UserProfile;
import com.digitax.payload.ApiRes;
import com.digitax.payload.request.ChangeEmailRequest;
import com.digitax.payload.request.ChangePasswordRequest;
import com.digitax.payload.request.ForgotPasswordRequest;
import com.digitax.payload.request.PhoneSigninRequest;
import com.digitax.payload.request.SendOtpRequest;
import com.digitax.payload.request.SigninRequest;
import com.digitax.payload.request.SignupRequest;
import com.digitax.payload.request.UpdatePasswordRequest;
import com.digitax.payload.request.VerifyChangeEmailRequest;
import com.digitax.payload.request.VerifyChangePassword;
import com.digitax.payload.request.VerifyOtpRequest;
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
import com.digitax.service.impl.UserProfileServiceImpl;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.digitax.controller.constants.Errors.*;
import com.digitax.constants.ResponseConstants;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(tags = {"AuthController"}, description = "Auth Controller")
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Value("${digitax.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    
    @Value("${digitax.app.jwtSecret}")
    private String jwtSecret;

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
    EmailService emailService;
    
    @Autowired 
    DeviceMetadataRepository deviceMetadataRepository;
    
    @Autowired 
    DeviceMetadataService devideMetadataService;
    
    @Autowired 
    UserProfileServiceImpl userProfileServiceImpl;
    
    
    /**##
     * 
     * @param loginRequest
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest loginRequest,HttpServletRequest request,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          }
    	    long jwtExpiry;
		   	  if(loginRequest.getDeviceType().equals("Android") || loginRequest.getDeviceType().equals("iOS")) {
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
            User detailsObjUserAftrLogin = userRepository.findByEmail(userDetails.getEmail());
            Object Sessionobj = new SessionResponse(jwt, jwtExpiry);
            JSONObject userDetailsObj = new JSONObject();
            userDetailsObj.put("id", userDetails.getId());
            userDetailsObj.put("username", userDetails.getUsername());
            userDetailsObj.put("email", userDetails.getEmail());
            userDetailsObj.put("authorities", userDetails.getAuthorities());
            userDetailsObj.put("phone", detailsObjUserAftrLogin.getPhone());

            JwtResponse obj = new JwtResponse(Sessionobj, userDetailsObj);
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", 200);
            statusObj.put("message", "SUCCESS");
            
            devideMetadataService.saveUserActivity(request,UserSession.getUserId(), detailsObjUserAftrLogin);
	        
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
     * @param loginRequest
     * @param request
     * @param bindingResult
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/mobile-number-signin")
    public ResponseEntity<?> authenticateUserByOtp(@Valid @RequestBody PhoneSigninRequest loginRequest,HttpServletRequest request,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          }
    	long jwtExpiry;
	   	  if(loginRequest.getDeviceType().equals("Android") || loginRequest.getDeviceType().equals("iOS")) {
	         jwtExpiry = jwtExpirationMs*360;
	         System.out.println(jwtExpiry);
		   	}
		   	else
		   	{
		   	 jwtExpiry = jwtExpirationMs;
		   	}
    	try 
    	{  
    		System.out.println(loginRequest.getPhone());
    	    Boolean isValid = smsService.verifyOTP(loginRequest.getOtp(),loginRequest.getCountryCode()+loginRequest.getPhone());
	    	if(isValid)
	    	{
	    	User  detailsObj = userRepository.findByPhone(loginRequest.getPhone());
	    	String jwt = jwtUtils.generateJwtTokenByUser(detailsObj,loginRequest.getDeviceType());
            Object Sessionobj = new SessionResponse(jwt, jwtExpiry);
            JSONObject userDetailsObj = new JSONObject();
            userDetailsObj.put("id", detailsObj.getId());
            userDetailsObj.put("username", detailsObj.getUsername());
            userDetailsObj.put("email", detailsObj.getEmail());
            userDetailsObj.put("authorities", detailsObj.getRoles());
            userDetailsObj.put("phone", detailsObj.getPhone());

            JwtResponse obj = new JwtResponse(Sessionobj, userDetailsObj);
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", 200);
            statusObj.put("message", "SUCCESS");
            
            devideMetadataService.saveUserActivity(request,detailsObj.getId(), detailsObj);
	        
            return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);
	    	}
	    	else
	    	{
	    		 JSONObject statusObj = new JSONObject();	   
			      statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
			      statusObj.put("message", "FAILURE");
		    	  JSONObject respObj = new JSONObject();
		    	  respObj.put("status","Invalid Otp");
			      return new ResponseEntity<>(ApiRes.success(respObj, statusObj), HttpStatus.OK);
	    	}
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
	   	  if(signUpRequest.getDeviceType().equals("iOS") || signUpRequest.getDeviceType().equals("Android")) {
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
    @PostMapping("/change-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          }  
		    try {
		       	 Optional<User> userObj = userRepository.findById(UserSession.getUserId());
		       	 if(userObj.isPresent())
		       	 { User detailsObj = userObj.get();
		       	    System.out.println(detailsObj.getEmail());
		       	    userProfileServiceImpl.updateUserPassword(detailsObj,encoder.encode(request.getPassword()));
		       	   	String emailData = emailService.changePasswordSupport(detailsObj.getEmail(),detailsObj.getUsername());
					System.out.println(emailData);
			       	JSONObject statusObj = new JSONObject();	   
			        statusObj.put("status_code", ResponseConstants.SUCCESS);
			        statusObj.put("message", "SUCCESS");
			        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
		       	 }
		       	 else
		       	 {
		       		JSONObject statusObj = new JSONObject();
			        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
			        statusObj.put("message", "User Does not exists");
			        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
		       	 }
		    	
		    }
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
		    statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		    statusObj.put("message", "FAILURE");
		    return new ResponseEntity<>(ApiRes.success(e, statusObj), HttpStatus.OK);	
			 }
        }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/send-otp")
    public ResponseEntity<?> SendOtp(@Valid @RequestBody SendOtpRequest request,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          }  
		    try {
		    	  smsService.sendOTP(request.getPhone());
		    	  JSONObject statusObj = new JSONObject();	   
			      statusObj.put("status_code", ResponseConstants.SUCCESS);
			      statusObj.put("message", "SUCCESS");
			      return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
              }
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
		    statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		    statusObj.put("message", "FAILURE");
		    return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
			 }	      
          }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest request,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          }  
		    try {
		    	  Boolean isValid = smsService.verifyOTP(request.getOtp(),request.getPhone());
		    	  JSONObject statusObj = new JSONObject();	   
			      statusObj.put("status_code", ResponseConstants.SUCCESS);
			      statusObj.put("message", "SUCCESS");
		    	  JSONObject respObj = new JSONObject();
		    	  respObj.put("status",isValid);
			      return new ResponseEntity<>(ApiRes.success(respObj, statusObj), HttpStatus.OK);
              }
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
		    statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		    statusObj.put("message", "FAILURE");
		    return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
			 }	      
          }
    
    @SuppressWarnings("unchecked")
	@GetMapping("/user-account-activity")
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
    
    
    @SuppressWarnings("unchecked")
	@GetMapping("/user-account-activity-by-device")
    public ResponseEntity<?> getUserAccountActivityByDevice(HttpServletRequest request) {
		try {
			InetAddress localHost = null;
			try {
				localHost = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	        NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
	        byte[] hardwareAddress = ni.getHardwareAddress();
	        String[] hardwareAddressDecode = new String[hardwareAddress.length];
	        if (hardwareAddress != null) {
	            for (int i = 0; i < hardwareAddress.length; i++) {
	            	hardwareAddressDecode[i] = String.format("%02X", hardwareAddress[i]);
	            }
	            System.out.println(String.join("-", hardwareAddressDecode));
	        }
	    List<DeviceMetadata> deviceDetails = deviceMetadataRepository.findAllByHardwareAddress(String.join("-", hardwareAddressDecode));
		JSONObject statusObj = new JSONObject();
        statusObj.put("status_code", ResponseConstants.SUCCESS);
        statusObj.put("message", "SUCCESS");
        return new ResponseEntity<>(ApiRes.success(deviceDetails, statusObj), HttpStatus.OK);	
		} 
		catch (Exception e) {
		  	   JSONObject statusObj = new JSONObject();
		         statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
		         statusObj.put("message", "FAILURE");
		         return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
		         }
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/change-email")
    public ResponseEntity<?> changeEmail(@Valid @RequestBody ChangeEmailRequest changeEmail,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          } 
    	try {
    	if (userRepository.existsByEmail(changeEmail.getNewEmail())) {
    		JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
	        statusObj.put("message", "User with this email already exists");
	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
    	}
    	else
    	{	
    		User detailsObj = userRepository.findByEmail(changeEmail.getEmail());
    		Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(detailsObj.getUsername().toLowerCase().trim(), changeEmail.getPassword()));
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		//userRepository.updateUserEmail(changeEmail.getNewEmail(),System.currentTimeMillis(),UserSession.getUserId());
            if(userDetails.getUsername() != null) {
            String token = Jwts.builder()
            		.setSubject("change email")
            		.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            		.claim("Email",changeEmail.getEmail())
            		.claim("NewEmail", changeEmail.getNewEmail())
            		.signWith(SignatureAlgorithm.HS512, jwtSecret )
            		.compact();
		        String emailData = emailService.changeEmailSupport(changeEmail.getEmail(),changeEmail.getNewEmail(),token,userDetails.getUsername());
				System.out.println(emailData);
		       	JSONObject statusObj = new JSONObject();	      	
		        statusObj.put("status_code", ResponseConstants.SUCCESS);
		        statusObj.put("message", "SUCCESS");
		        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
            }
            else
            {
            	JSONObject statusObj = new JSONObject();
    	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
    	        statusObj.put("message", "User does't exists");
    	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
            }
    	  }    
			}
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
	        statusObj.put("message", "FAILURE");
	        return new ResponseEntity<>(ApiRes.success("Bad credentials", statusObj), HttpStatus.OK);	
			 }
        
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/verify-change-email")
    public ResponseEntity<?> verifyChangeEmail(@Valid @RequestBody VerifyChangeEmailRequest ChangeEmailRequest,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          } 
    	try {
    		boolean isVaid = jwtUtils.validateJwtToken(ChangeEmailRequest.getVerifyToken());
    		
    	if (isVaid) {
       		Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(ChangeEmailRequest.getVerifyToken())
            .getBody();
       		User detailsObj = userRepository.findByEmail(claims.get("Email").toString());
       		if (userRepository.existsByEmail(claims.get("NewEmail").toString())) {
        		JSONObject statusObj = new JSONObject();
    	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
    	        statusObj.put("message", "Your new email is already taken");
    	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
        	}
       	    userRepository.updateUserEmail(claims.get("NewEmail").toString(),System.currentTimeMillis(),detailsObj.getId());
    		JSONObject statusObj = new JSONObject();	      	
	        statusObj.put("status_code", ResponseConstants.SUCCESS);
	        statusObj.put("message", "SUCCESS");
	        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
    	}
    	else
    	{
    		JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
	        statusObj.put("message", "Token is not valid");
	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
    	}
  
			}
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
	        statusObj.put("message", "FAILURE");
	        return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
			 }
        
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/forgot-password-request")
    public ResponseEntity<?> forgotPasswordRequest(@Valid @RequestBody ForgotPasswordRequest forgotPassword,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          } 
    	try {
    	if (!userRepository.existsByEmail(forgotPassword.getEmail())) {
    		JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
	        statusObj.put("message", "User with this email does't exists");
	        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
    	}
    	else
    	{	
    		User detailsObj = userRepository.findByEmail(forgotPassword.getEmail());	
            String token = Jwts.builder()
            		.setSubject("Forgot password")
            		.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            		.claim("UserId",detailsObj.getId())
            		.claim("Email", detailsObj.getEmail())
            		.claim("UserName", detailsObj.getUsername())
            		.signWith(SignatureAlgorithm.HS512, jwtSecret )
            		.compact();
		        String emailData = emailService.forgotPasswordSupport(forgotPassword.getEmail(),token,detailsObj.getUsername());
				System.out.println(emailData);
		       	JSONObject statusObj = new JSONObject();	      	
		        statusObj.put("status_code", ResponseConstants.SUCCESS);
		        statusObj.put("message", "SUCCESS");
		        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
               }
            
			}
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
	        statusObj.put("message", "FAILURE");
	        return new ResponseEntity<>(ApiRes.success("Bad credentials", statusObj), HttpStatus.OK);	
			 }
        
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/forgot-password-verify")
    public ResponseEntity<?> forgotPasswordVerify(@Valid @RequestBody VerifyChangePassword forgotPassword,BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
			ArrayList<?> errors = (ArrayList<?>) bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			 JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", errors);
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
          } 
    	try {
    		boolean isVaid = jwtUtils.validateJwtToken(forgotPassword.getVerifyToken());
    		
    	if (isVaid) {
       		Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(forgotPassword.getVerifyToken())
            .getBody();
       		Optional<User> detailsObj = userRepository.findByUsername(claims.get("UserName").toString());
       		User userDetails  = detailsObj.get();
       		userDetails.setPassword(encoder.encode(forgotPassword.getNewPassword()));
       		userRepository.save(userDetails);
       		JSONObject statusObj = new JSONObject();	      	
	        statusObj.put("status_code", ResponseConstants.SUCCESS);
	        statusObj.put("message", "SUCCESS");
	        return new ResponseEntity<>(ApiRes.success(null, statusObj), HttpStatus.OK);
	    	}
	    	else
	    	{
	    		JSONObject statusObj = new JSONObject();
		        statusObj.put("status_code", ResponseConstants.VALIDATION_ERROR);
		        statusObj.put("message", "Token is not valid");
		        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(null,statusObj));
	    	}
	  
			}
			catch (Exception e) {
			JSONObject statusObj = new JSONObject();
	        statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
	        statusObj.put("message", "FAILURE");
	        return new ResponseEntity<>(ApiRes.success(e.getMessage(), statusObj), HttpStatus.OK);	
	    }
     }
    
 
}