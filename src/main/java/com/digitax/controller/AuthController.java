package com.digitax.controller;

import com.digitax.model.ERole;
import com.digitax.model.Role;
import com.digitax.model.User;
import com.digitax.payload.ApiRes;
import com.digitax.payload.request.SignupRequest;
import com.digitax.payload.response.JwtResponse;
import com.digitax.repository.RoleRepository;
import com.digitax.repository.UserRepository;
import com.digitax.security.jwt.AuthEntryPointJwt;
import com.digitax.security.jwt.JwtUtils;
import com.digitax.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

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

	/**
	 * Make /signin request
     *
     * <p>Endpoint will determine if the user record exists. If the user exists a session
     * for that user is established and user information returned</p>
     *
	 * @param signinRequest Provided request model for making /signin request
	 * @return
	 */
	@PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

            // set authentication
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // generate JWT using received claims
            String jwt = jwtUtils.generateJwtToken(authentication);

            // user details
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // list if roles
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            Object obj = new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
            return new ResponseEntity<>(ApiRes.success(obj).setMessage("LogIn Success."), HttpStatus.OK);
        } catch (Exception e) {
            //logger.error("Unauthorized user.", e);
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail().setMessage(ERROR_UNAUTHORIZED_USER));
        }
    }

	/**
     * Make /signup request
     *
     * <p>Endpoint will create a user record. After successfully creating the record a
     * session for that user is established and user information returned.</p>
     *
     * @param signinRequest Provided request model for making /signup request
     * @return
	 */
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail().setMessage(ERROR_USERNAME_IN_USE));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail().setMessage(ERROR_EMAIL_IN_USE));
        }

        if (userRepository.existsByPhone(signupRequest.getPhone())) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail().setMessage(ERROR_PHONE_NUMBER_IN_USE));
        }

        // create new user's account
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getPhone());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roleSet = new HashSet<>();

        // establish and set user role
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roleSet.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(userRole);
                }
            });
        }
        // set user role
        user.setRoles(roleSet);
        userRepository.save(user);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signupRequest.getUsername(), signupRequest.getPassword()));

            // set authentication
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // generate JWT using received claims
            String jwt = jwtUtils.generateJwtToken(authentication);

            // user details
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // list if roles
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            Object obj = new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
            return new ResponseEntity<>(ApiRes.success(obj).setMessage("Sign Up Success."), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Unauthorized error: {}", e);
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail().setMessage(ERROR_DEFAULT));
        }
    }
}