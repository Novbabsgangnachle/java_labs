package ru.novbabsgangnachle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.novbabsgangnachle.dto.RegisterDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userDetailsManager.userExists(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        var encoded = passwordEncoder.encode(registerDto.getPassword());
        var user = User.withUsername(registerDto.getUsername())
                .password(encoded)
                .roles(registerDto.getRoles().toArray(String[]::new))
                .build();
        userDetailsManager.createUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
