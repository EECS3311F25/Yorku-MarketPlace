package eecs3311.group.p.Marketplace.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eecs3311.group.p.Marketplace.model.User;
import eecs3311.group.p.Marketplace.model.UserRepository;
/**
 * Service layer for handling all authentication-related business logic,
 * such as user creation, verification, and password management.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    /**
     * Constructs a new AuthService with injected dependencies.
     *
     * @param userRepository  The repository for user data access.
     * @param passwordEncoder The bean for encoding passwords (injected from SecurityConfig).
     */

     public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
     /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the User if found, or an empty Optional.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    /**
     * Finds a user by their email address.
     *
     * @param email The email address to search for.
     * @return An Optional containing the User if found, or an empty Optional.
     */
    public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
    }

    /**
     * Creates a new, unverified user in the database.
     * This method saves the user with a hashed password, a verification code,
     * and a 'verified' status of false.
     *
     * @param username         The new user's username.
     * @param password         The new user's plain-text password.
     * @param email            The new user's email address.
     * @param verificationCode The generated code for email verification.
     * @return The saved User entity.
     */
    public User signupUnverified(String username, String password, String email, String verificationCode) {
        String hash = passwordEncoder.encode(password);
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(hash);
        u.setEmail(email);
        u.setVerificationCode(verificationCode);
        u.setVerified(false); // Explicitly set to false
        return userRepository.save(u);
    }
    
    /**
     * Attempts to verify a user account using the provided code.
     * If the username exists, the code matches, and the user is not already verified,
     * the user's status is set to 'verified' and the code is cleared.
     *
     * @param username The username of the user to verify.
     * @param code     The 6-digit verification code.
     * @return true if verification was successful, false otherwise.
     */
    public boolean verifyUser(String username, String code) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            return false; // User not found
        }
        
        User user = userOpt.get();
        
        // Check if code is correct and user isn't already verified
        if (user.getVerificationCode() != null && user.getVerificationCode().equals(code) && !user.isVerified()) {
            user.setVerified(true);
            user.setVerificationCode(null); // Clear the code after successful use
            userRepository.save(user);
            return true;
        }
        
        return false; // Code was wrong or user already verified
    }

    /**
     * Creates a password reset token for a user found by email.
     * The token is valid for 1 hour.
     *
     * @param email The user's email address.
     * @return The generated UUID token, or null if no user is found with that email.
     */
    public String createPasswordResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return null; // Don't reveal that the user doesn't exist
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1)); // Token is valid for 1 hour
        userRepository.save(user);
        return token;
    }

    /**
     * Validates a password reset token.
     * @param token The token to validate
     * @return true if the token is valid and not expired, false otherwise.
     */
    public boolean validatePasswordResetToken(String token) {
        Optional<User> userOpt = userRepository.findByPasswordResetToken(token);
        
        if (userOpt.isEmpty()) {
            return false; // Token not found
        }
        
        User user = userOpt.get();
        
        // Check if token is expired
        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return false; // Token expired
        }

        return true; // Token is valid
    }

    /**
     * Resets a user's password using a valid token.
     * @param token The reset token
     * @param newPassword The new, unhashed password
     * @return true on success, false if token was invalid.
     */
    public boolean resetPassword(String token, String newPassword) {
        // First, validate the token
        if (!validatePasswordResetToken(token)) {
            return false;
        }

        // We know user is present because token was valid
        User user = userRepository.findByPasswordResetToken(token).get();

        // Set new password
        String newHash = passwordEncoder.encode(newPassword);
        user.setPasswordHash(newHash);

        // Invalidate the token so it can't be used again
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);

        userRepository.save(user);
        return true;
    }

}