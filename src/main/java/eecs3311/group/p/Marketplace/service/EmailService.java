package eecs3311.group.p.Marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marketplace@356952d458b92ab0.maileroo.org"); // Set your "from" address
        message.setTo(toEmail);
        message.setSubject("Marketplace Account Verification Code");
        message.setText("Your verification code is: " + code + "\n\nThis code will expire shortly.");
        // System.out.println("Prepared Message");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
        
        // System.out.println("Email Sent");
    }

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marketplace@356952d458b92ab0.maileroo.org");
        message.setTo(toEmail);
        message.setSubject("Marketplace Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetLink);
        mailSender.send(message);
    }
}