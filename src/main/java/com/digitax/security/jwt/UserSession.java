package com.digitax.security.jwt;




import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.digitax.security.services.UserDetailsImpl;

public class UserSession {

    public static long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return userPrincipal.getId();
    }

    

}
