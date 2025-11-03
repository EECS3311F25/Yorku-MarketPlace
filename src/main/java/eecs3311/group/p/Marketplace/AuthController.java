package eecs3311.group.p.Marketplace;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eecs3311.group.p.Marketplace.model.User;
import eecs3311.group.p.Marketplace.service.AuthService;
import eecs3311.group.p.Marketplace.service.EmailService; // <-- Import EmailService

import java.util.Optional;
import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession; // <-- Import HttpSession

@Controller
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    public AuthController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService; // <-- Add to constructor
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) boolean verified, Model model) {
        if (verified) {
            model.addAttribute("success", "Verification successful! Please log in.");
        }
        return "login";
    }
    // Old Login Method
    // @PostMapping("/login")
    // public String loginSubmit(@RequestParam String username,
    //                           @RequestParam String password,
    //                           Model model) {
    //     Optional<User> user = authService.login(username, password);
    //     if (user.isPresent()) {
    //         model.addAttribute("username", username);
    //         return "home";
    //     }
    //     // This error message is secure and correct for both bad password AND unverified user
    //     model.addAttribute("error", "Invalid username or password");
    //     return "login";
    // }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               HttpSession session, // <-- Add session
                               Model model) {
        
        // 1. Validate Email Domain
        if (email == null || (!email.endsWith("@yorku.ca") && !email.endsWith("@my.yorku.ca"))) {
            model.addAttribute("error", "Email must be a valid @yorku.ca or @my.yorku.ca address.");
            return "signup";
        }

        // 2. Check if user already exists
        //    (You should change this to a dedicated findByUsername or userExists method)
        if (authService.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }

        if (authService.findByEmail(email).isPresent()) {
            model.addAttribute("error","An account with this email already exists.");
            return "signup";
        }

        // 3. Generate verification code
        String verificationCode = generateVerificationCode();

        // 4. Create *unverified* user in database
        //    (You must implement signupUnverified in AuthService)
        try {
            authService.signupUnverified(username, password, email, verificationCode);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred. Please try again.");
            return "signup";
        }

        // 5. Send verification email
        try {
            System.out.println("Sending email now");
            emailService.sendVerificationEmail(email, verificationCode);
        } catch (Exception e) {
            // NOTE: In production, you'd handle this more gracefully
            // (e.g., delete the unverified user, or queue for retry)
            System.out.println("Email Failed"+e);
            model.addAttribute("error", "Could not send verification email. Please check the address and try again.");
            return "signup";
        }

        // 6. Store username in session and redirect to verify page
        session.setAttribute("username_to_verify", username);
        session.setAttribute("email_to_verify", email); // Optional: for display on verify page
        return "redirect:/verify";
    }

    // +++ NEW ENDPOINTS FOR VERIFICATION +++

    @GetMapping("/verify")
    public String verifyForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username_to_verify");
        if (username == null) {
            return "redirect:/signup"; // No verification in progress
        }
        
        model.addAttribute("email", session.getAttribute("email_to_verify"));
        return "verify";
    }

    @PostMapping("/verify")
    public String verifySubmit(@RequestParam String code,
                               HttpSession session,
                               Model model) {
        
        String username = (String) session.getAttribute("username_to_verify");
        if (username == null) {
            model.addAttribute("error", "Your session has expired. Please sign up again.");
            return "verify"; // Stay on verify page, but show session error
        }

        // 1. Attempt to verify user in service
        //    (You must implement verifyUser in AuthService)
        boolean success = authService.verifyUser(username, code);

        if (success) {
            // 2. Clear session attributes and redirect to login with success
            session.removeAttribute("username_to_verify");
            session.removeAttribute("email_to_verify");
            return "redirect:/login?verified=true";
        } else {
            // 3. Show error on verify page
            model.addAttribute("error", "Invalid verification code. Please try again.");
            model.addAttribute("email", session.getAttribute("email_to_verify"));
            return "verify";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam String email, 
                                       Model model, 
                                       HttpServletRequest request) {
        
        String token = authService.createPasswordResetToken(email);
        
        if (token != null) {
            try {
                // Build the base URL (e.g., http://localhost:8080)
                String baseUrl = request.getScheme() + "://" + request.getServerName() + 
                                 (request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : ":" + request.getServerPort());
                String resetLink = baseUrl + "/reset-password?token=" + token;
                
                emailService.sendPasswordResetEmail(email, resetLink);
            } catch (Exception e) {
                // Log the exception (e)
                model.addAttribute("error", "Could not send reset email. Please try again later.");
                return "forgot-password";
            }
        }

        // IMPORTANT: Show a generic success message even if the email doesn't exist.
        // This prevents attackers from guessing which emails are registered.
        model.addAttribute("success", "If an account with that email exists, a password reset link has been sent.");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {
        
        boolean isValid = authService.validatePasswordResetToken(token);
        if (!isValid) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired password reset link.");
            return "redirect:/login";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordSubmit(@RequestParam String token,
                                      @RequestParam String password,
                                      @RequestParam String confirmPassword,
                                      RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/reset-password?token=" + token;
        }

        boolean success = authService.resetPassword(token, password);

        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired password reset link. Please try again.");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("success", "Your password has been reset successfully. Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }



    private String generateVerificationCode() {
        // Generate a 6-digit code
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}