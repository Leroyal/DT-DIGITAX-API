package com.digitax.controller;

import com.digitax.model.ERole;
import com.digitax.model.Role;
import com.digitax.model.User;
import com.digitax.payload.ApiRes;
import com.digitax.payload.request.SigninRequest;
import com.digitax.payload.request.SignupRequest;
import com.digitax.payload.response.JwtResponse;
import com.digitax.payload.response.SessionResponse;
import com.digitax.repository.RoleRepository;
import com.digitax.repository.UserRepository;
import com.digitax.security.jwt.AuthEntryPointJwt;
import com.digitax.security.jwt.JwtUtils;
import com.digitax.security.services.UserDetailsImpl;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.digitax.controller.constants.Errors.*;
import com.digitax.constants.ResponseConstants;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
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
    JwtUtils jwtUtils;

    @SuppressWarnings("unchecked")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            Object Sessionobj = new SessionResponse(jwt, jwtExpirationMs);
            JSONObject userDetailsObj = new JSONObject();
            userDetailsObj.put("id", userDetails.getId());
            userDetailsObj.put("username", userDetails.getUsername());
            userDetailsObj.put("email", userDetails.getEmail());
            userDetailsObj.put("authorities", userDetails.getAuthorities());

            JwtResponse obj = new JwtResponse(Sessionobj, userDetailsObj);
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", 200);
            statusObj.put("message", "SUCCESS");
            return new ResponseEntity<>(ApiRes.success(obj, statusObj), HttpStatus.OK);
       } catch (Exception e) {
    	   JSONObject statusObj = new JSONObject();
           statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
           statusObj.put("message", "FAILURE");
           logger.error("Unauthorized error: {}");
           return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));}
    }

  
    @SuppressWarnings({"unchecked"})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            JSONObject statusObj = new JSONObject();
            System.out.println(statusObj.toJSONString());
            statusObj.put("status_code", ResponseConstants.USER_ALREADY_EXISTS);
            statusObj.put("message", "Username is already taken!");
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));

        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code", ResponseConstants.USER_ALREADY_EXISTS);
            statusObj.put("message", "Email is already in use");
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));

        }

        if (userRepository.existsByPhone(signUpRequest.getPhone())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put("status_code",ResponseConstants.USER_ALREADY_EXISTS);
            statusObj.put("message", "Phone is already in use!");
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));

        }

    

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhone(),
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
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles1 = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());


            Object Sessionobj = new SessionResponse(jwt, jwtExpirationMs);

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
}