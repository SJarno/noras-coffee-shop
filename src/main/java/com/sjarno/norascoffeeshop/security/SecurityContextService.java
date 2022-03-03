package com.sjarno.norascoffeeshop.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    /* @Autowired
    private AuthenticationManager authManager; */

    /* public void refreshAuth(
        String username, 
        String password, 
        HttpServletRequest request) {
        
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

    } */

    public Authentication getSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
}
