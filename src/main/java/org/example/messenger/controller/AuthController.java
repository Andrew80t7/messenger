package org.example.messenger.controller;

import org.example.messenger.model.User;
import org.example.messenger.model.UserDto;
import org.example.messenger.service.UserService;
import org.example.messenger.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto.getUsername(), userDto.getPassword(), passwordEncoder);
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token, "user", user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userDto.getUsername(),
                    userDto.getPassword()
                )
            );

            User user = (User) userService.loadUserByUsername(userDto.getUsername());
            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(Map.of(
                "token", token,
                "username", user.getUsername()
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Неверное имя пользователя или пароль");
        }
    }
}