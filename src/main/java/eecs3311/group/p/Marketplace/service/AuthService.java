package eecs3311.group.p.Marketplace.service;

import java.util.Optional;

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

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()));
    }

    public User signup(String username, String password, String email) {
        String hash = passwordEncoder.encode(password);
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(hash);
        u.setEmail(email);
        return userRepository.save(u);
    }
}
