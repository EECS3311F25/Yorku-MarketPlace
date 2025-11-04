package eecs3311.group.p.Marketplace.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import eecs3311.group.p.Marketplace.service.CustomUserDetailsService;
/**
 * Main Spring Security configuration class for the application.
 *
 * This class defines the security filter chain, password encoder, and
 * authentication provider. It configures which routes are public,
 * how form-based login behaves, and the logout process.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    /**
     * The custom user details service that links Spring Security to the user database.
     */
    private final CustomUserDetailsService userDetailsService;
    /**
     * Constructs the SecurityConfig and injects the required user details service.
     *
     * @param userDetailsService The custom service for loading user-specific data.
     */
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    /**
     * Provides the password encoder bean for the application.
     * Uses BCrypt for strong, salted password hashing.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Configures the primary authentication provider.
     * This bean connects Spring Security to the {@link CustomUserDetailsService} (for finding users)
     * and the {@link PasswordEncoder} (for verifying passwords).
     *
     * @param passwordEncoder The application's password encoder bean.
     * @return A configured DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }
    /**
     * Defines the main security filter chain that protects all HTTP requests.
     *
     * @param http The HttpSecurity object to configure.
     * @param authProvider The custom authentication provider.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
        http
            .authenticationProvider(authProvider)
            .authorizeHttpRequests(authorize -> authorize
                // public endpoints
                .requestMatchers("/", "/login", "/signup", "/verify", "/forgot-password", "/reset-password", "/css/**", "/js/**", "/images/**").permitAll()
                // static content
                .requestMatchers("/webjars/**", "/favicon.ico").permitAll()
                // API or admin could be further locked down
                .anyRequest().authenticated()
            )
            // form login configuration
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")        // POST /login handled by Spring Security
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // logout configuration
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            // CSRF is enabled by default; keep it
            .csrf(Customizer.withDefaults());

        return http.build();
    }
}
