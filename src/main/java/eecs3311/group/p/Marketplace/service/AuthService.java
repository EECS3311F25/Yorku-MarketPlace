package eecs3311.group.p.Marketplace.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eecs3311.group.p.Marketplace.model.User;
import eecs3311.group.p.Marketplace.model.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Finds a user by their username.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    /**
    * Finds a user by their email.
    */
    public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
    }

    /**
     *
     * Logs a user in, checking both password and verification status.
     */
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return Optional.empty(); // User not found
        }
        User user = userOpt.get();
        if (!user.isVerified()) {
            return Optional.empty();
        }
        // Check if password matches AND user is verified
        if (passwordEncoder.matches(password, user.getPasswordHash()) && user.isVerified()) {
            return userOpt; // Success!
        }

        return Optional.empty(); // Failed login (bad pass or not verified)
    }

    /**
     *
     * Create Account but check for email/username to prevent duplicates.
     */
    
    /**
     *
     * Creates a new, unverified user with a verification code.
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
     * 
     * Attempts to verify a user with the given code.
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
     * @param email The user's email
     * @return The generated token, or null if user not found.
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