package eecs3311.group.p.Marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import eecs3311.group.p.Marketplace.service.AuthService;
import eecs3311.group.p.Marketplace.service.EmailService;
import eecs3311.group.p.Marketplace.service.ListingService;

import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for handling user authentication, registration, and password recovery processes.
 * Maps URLs related to login, signup, verification, and password resets.
 */
@Controller
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final ListingService listingService;


    /**
     * Constructs a new AuthController with the necessary authentication and email services.
     *
     * @param authService  The service responsible for authentication logic.
     * @param emailService The service responsible for sending emails.
     * @param listingService the service responsible for showing/creating Listings.
     */
    public AuthController(AuthService authService, EmailService emailService, ListingService listingService) {
        this.authService = authService;
        this.emailService = emailService;
        this.listingService = listingService;
    }

    /**
     * Redirects the root URL ("/") to the login page.
     *
     * @return A redirect string to the login page.
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    /**
     * Displays the login form.
     * Also handles displaying a success message after email verification.
     *
     * @param verified True if the user is coming from a successful email verification, false otherwise.
     * @param model    The Spring model to add attributes to.
     * @return The name of the login view template.
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) boolean verified, Model model) {
        if (verified) {
            model.addAttribute("success", "Verification successful! Please log in.");
        }
        return "login";
    }

    /**
     * Displays the user registration (signup) form.
     *
     * @return The name of the signup view template.
     */
    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    /**
     * Handles the submission of the registration form.
     * Validates user input, checks for existing users, creates an unverified user,
     * and sends a verification email.
     *
     * @param username The desired username.
     * @param password The desired password.
     * @param email    The user's email address (must be @yorku.ca or @my.yorku.ca).
     * @param session  The HTTP session to store verification data.
     * @param model    The Spring model to add error attributes to.
     * @return A redirect string to the verification page on success, or the signup page on failure.
     */
    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               HttpSession session,
                               Model model) {
        
        // 1. Validate Email Domain
        if (email == null || (!email.endsWith("@yorku.ca") && !email.endsWith("@my.yorku.ca"))) {
            model.addAttribute("error", "Email must be a valid @yorku.ca or @my.yorku.ca address.");
            return "signup";
        }

        // 2. Check if user already exists
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
        try {
            authService.signupUnverified(username, password, email, verificationCode);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred. Please try again.");
            return "signup";
        }

        // 5. Send verification email
        try {
            emailService.sendVerificationEmail(email, verificationCode);
        } catch (Exception e) {
            model.addAttribute("error", "Could not send verification email. Please check the address and try again.");
            return "signup";
        }

        // 6. Store username in session and redirect to verify page
        session.setAttribute("username_to_verify", username);
        session.setAttribute("email_to_verify", email);
        return "redirect:/verify";
    }

    /**
     * Displays the email verification form.
     * Retrieves the user's email from the session to display it on the page.
     *
     * @param session The HTTP session containing verification data.
     * @param model   The Spring model to add attributes to.
     * @return The name of the verify view template, or a redirect to signup if the session is invalid.
     */
    @GetMapping("/verify")
    public String verifyForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username_to_verify");
        if (username == null) {
            return "redirect:/signup"; // No verification in progress
        }
        
        model.addAttribute("email", session.getAttribute("email_to_verify"));
        return "verify";
    }

    /**
     * Handles the submission of the email verification code.
     *
     * @param code    The 6-digit verification code entered by the user.
     * @param session The HTTP session containing verification data.
     * @param model   The Spring model to add error attributes to.
     * @return A redirect string to the login page on success, or the verify page on failure.
     */
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

    /**
     * Displays the "forgot password" form.
     *
     * @return The name of the forgot-password view template.
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot-password";
    }

    /**
     * Handles the submission of the "forgot password" form.
     * Generates a password reset token and sends a reset link to the user's email.
     *
     * @param email   The email address submitted by the user.
     * @param model   The Spring model to add attributes to.
     * @param request The HTTP request, used to build the reset link URL.
     * @return The name of the forgot-password view template, displaying a success or error message.
     */
    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam String email, 
                                       Model model, 
                                       HttpServletRequest request) {
        
        String token = authService.createPasswordResetToken(email);
        
        if (token != null) {
            try {
                String baseUrl = request.getScheme() + "://" + request.getServerName() + 
                                 (request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : ":" + request.getServerPort());
                String resetLink = baseUrl + "/reset-password?token=" + token;
                
                emailService.sendPasswordResetEmail(email, resetLink);
            } catch (Exception e) {
                model.addAttribute("error", "Could not send reset email. Please try again later.");
                return "forgot-password";
            }
        }
        // Always show a generic success message to prevent email enumeration attacks
        model.addAttribute("success", "If an account with that email exists, a password reset link has been sent.");
        return "forgot-password";
    }

    /**
     * Displays the password reset form, if the provided token is valid.
     *
     * @param token              The password reset token from the URL.
     * @param model              The Spring model to add the token to.
     * @param redirectAttributes Attributes for a redirect, used to show an error on the login page.
     * @return The name of the reset-password view template, or a redirect to login if the token is invalid.
     */
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

    /**
     * Handles the submission of the new password.
     *
     * @param token              The password reset token (from a hidden form field).
     * @param password           The new password.
     * @param confirmPassword    The new password confirmation.
     * @param redirectAttributes Attributes for a redirect, used to show success/error messages.
     * @return A redirect string to the login page on success, or back to the reset page on failure.
     */
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

    /**
     * Displays the user's home page after successful authentication.
     * Assumes Spring Security is configured to protect this route.
     *
     * @return The name of the home view template.
     */
    @GetMapping("/home")
    public String home(Model model) {
    model.addAttribute("listings", listingService.getAllListings());
    return "home";
    }

    /**
     * Displays a generic error page.
     *
     * @return The name of the error view template.
     */
    @GetMapping("/error")
    public String error(){
        return "error";
    }

    /**
     * Generates a random 6-digit verification code as a string.
     *
     * @return A zero-padded 6-digit string (e.g., "001234").
     */
    private String generateVerificationCode() {
        // Generate a 6-digit code
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}

