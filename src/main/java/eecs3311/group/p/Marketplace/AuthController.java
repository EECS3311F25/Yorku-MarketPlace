package eecs3311.group.p.Marketplace;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eecs3311.group.p.Marketplace.model.User;
import eecs3311.group.p.Marketplace.service.AuthService;

import java.util.Optional;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              Model model) {
        Optional<User> user = authService.login(username, password);
        if (user.isPresent()) {
            model.addAttribute("username", username);
            return "home";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(required = false) String email,
                               Model model) {
        // check existing
        Optional<User> existing = authService.login(username, password);
        if (existing.isPresent()) {
            model.addAttribute("error", "User already exists");
            return "signup";
        }
        User u = authService.signup(username, password, email);
        model.addAttribute("username", u.getUsername());
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
