package com.example.vmmanagement.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityContext {

    private final JwtUtil jwtUtil;

    public SecurityContext(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Get the current authenticated user's email
     * @return the email of the authenticated user
     */
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && 
            authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
    
    /**
     * Get the current authenticated user's ID from the JWT token
     * @return the UUID of the authenticated user
     */
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object credentials = authentication.getCredentials();
            if (credentials != null && credentials instanceof String) {
                String jwt = (String) credentials;
                try {
                    return jwtUtil.extractUserId(jwt);
                } catch (Exception e) {
                    // Log error silently, authentication token might not include userId
                }
            }
        }
        return null;
    }
} 