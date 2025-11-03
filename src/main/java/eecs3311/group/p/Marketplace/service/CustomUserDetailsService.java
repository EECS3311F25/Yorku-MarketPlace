package eecs3311.group.p.Marketplace.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.LockedException; // <-- IMPORT ADDED
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import eecs3311.group.p.Marketplace.model.User;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * This service is responsible for loading a user's details from the database
 * (via AuthService) and converting them into a UserDetails object that
 * Spring Security can understand and use for authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthService authService;

    /**
     * Constructs the service with the required AuthService for data access.
     *
     * @param authService The service used to find user data.
     */
    public CustomUserDetailsService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Loads a user by their username and wraps them in a Spring Security UserDetails object.
     *
     * @param username The username (case-sensitive) to search for.
     * @return a UserDetails object containing the user's credentials and authorities.
     * @throws UsernameNotFoundException if the user could not be found.
     * @throws LockedException if the user is found but their account is not verified.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, LockedException {
        // 1. Find the user in the database
        User user = authService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Block login if the account is not verified.
        // This is the correct implementation of your email verification check.
        if (!user.isVerified()) {
            throw new LockedException("User account is not verified. Please check your email.");
        }

        // 3. Grant a default authority. This can be expanded later.
        Collection<? extends GrantedAuthority> authorities = 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // 4. Build the Spring Security User object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash()) // The BCrypt hash from the DB
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false) // Account is not locked (but we check verification above)
                .credentialsExpired(false)
                .disabled(false) // Account is enabled
                .build();
    }
}