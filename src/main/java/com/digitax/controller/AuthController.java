package com.digitax.controller;

import com.digitax.constants.Constants;
import com.digitax.constants.HttpStatusCode;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    // exceptions
    private static final String RUNTIME_EXCEPTION_ROLE_NOT_FOUND = "Error: Role is not found.";

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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest loginRequest) {
        // represents the token for an authentication request or for an authenticated principal
        // once the request has been processed.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        // return response entity
        return authenticate(authentication);
    }

    @SuppressWarnings({"unchecked"})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // check if username already exists
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put(Constants.MESSAGE, HttpStatusCode.USERNAME_ALREADY_EXISTS);
            statusObj.put(Constants.STATUS_CODE, HttpStatusCode.USERNAME_ALREADY_EXISTS.code());
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));
        }

        // check if email already exists
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put(Constants.MESSAGE, HttpStatusCode.EMAIL_ALREADY_EXISTS);
            statusObj.put(Constants.STATUS_CODE, HttpStatusCode.EMAIL_ALREADY_EXISTS.code());
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));
        }

        // check if phone number already exists
        if (userRepository.existsByPhone(signUpRequest.getPhone())) {
            JSONObject statusObj = new JSONObject();
            statusObj.put(Constants.MESSAGE, HttpStatusCode.PHONE_NUMBER_ALREADY_EXISTS);
            statusObj.put(Constants.STATUS_CODE, HttpStatusCode.PHONE_NUMBER_ALREADY_EXISTS.code());
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));
        }

        // create new user account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhone(),
                System.currentTimeMillis());

        // collection that contains no duplicate elements
        // initialize role variables
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(RUNTIME_EXCEPTION_ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            // check role types
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(RUNTIME_EXCEPTION_ROLE_NOT_FOUND));
                        // add role type
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException(RUNTIME_EXCEPTION_ROLE_NOT_FOUND));
                        // add role type
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(RUNTIME_EXCEPTION_ROLE_NOT_FOUND));
                        // add role type
                        roles.add(userRole);
                }
            });
        }

        // attach role to user and save to database
        user.setRoles(roles);
        userRepository.save(user);

        // represents the token for an authentication request or for an authenticated principal
        // once the request has been processed.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getUsername(),
                        signUpRequest.getPassword())
        );

        // return response entity
        return authenticate(authentication);
    }

    /**
     * Method is used to authenticate user.
     *
     * @param authentication Authentication object containing username and password information.
     * @return [ResponseEntity] Extension of HttpEntity that adds an HttpStatus status code.
     */
    @SuppressWarnings({"unchecked"})
    private ResponseEntity<?> authenticate(Authentication authentication) {
        // once the request has been authenticated, the Authentication will usually be stored in a
        // thread-local SecurityContext managed by the SecurityContextHolder by the authentication
        // mechanism which is being used.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        // user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Object sessionObj = new SessionResponse(jwt, jwtExpirationMs);

        try {
            // user details object
            JSONObject userDetailsObj = new JSONObject();
            userDetailsObj.put(Constants.ID, userDetails.getId());
            userDetailsObj.put(Constants.USERNAME, userDetails.getUsername());
            userDetailsObj.put(Constants.EMAIL, userDetails.getEmail());
            userDetailsObj.put(Constants.AUTHORITIES, userDetails.getAuthorities());

            // jwt response
            JwtResponse response = new JwtResponse(sessionObj, userDetailsObj);

            // status object
            JSONObject statusObj = new JSONObject();
            statusObj.put(Constants.MESSAGE, HttpStatusCode.SUCCESS);
            statusObj.put(Constants.STATUS_CODE, HttpStatusCode.SUCCESS.code());
            // return response entity
            return new ResponseEntity<>(ApiRes.success(response, statusObj), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unauthorized error: " + e.getMessage());

            // status object
            JSONObject statusObj = new JSONObject();
            statusObj.put(Constants.MESSAGE, HttpStatusCode.INTERNAL_SERVER_ERROR);
            statusObj.put(Constants.STATUS_CODE, HttpStatusCode.INTERNAL_SERVER_ERROR.code());

            // return response entity
            return ResponseEntity.status(HttpStatus.OK).body(ApiRes.fail(statusObj));
        }
    }
}