package eecs3311.group.p.Marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service responsible for constructing and sending application-related emails,
 * such as verification codes and password reset links.
 */
@Service
public class EmailService {

    /**
     * The Spring-managed sender for handling email dispatch.
     * This is configured in application.properties.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a 6-digit verification code to a user's email address.
     *
     * @param toEmail The recipient's email address.
     * @param code    The 6-digit verification code string.
     */
    public void sendVerificationEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marketplace@356952d458b92ab0.maileroo.org");
        message.setTo(toEmail);
        message.setSubject("Marketplace Account Verification Code");
        message.setText("Your verification code is: " + code + "\n\nThis code will expire shortly.");
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send verification email err:"+ e.getMessage());
        }
    }

    /**
     * Sends a password reset link to a user's email address.
     * This method may throw a MailException if sending fails.
     *
     * @param toEmail   The recipient's email address.
     * @param resetLink The full URL for the password reset page, including the token.
     */
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marketplace@356952d458b92ab0.maileroo.org");
        message.setTo(toEmail);
        message.setSubject("Marketplace Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetLink);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send Password reset email err:"+ e.getMessage());
        }
    }
}