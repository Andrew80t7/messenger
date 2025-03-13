package org.example.messenger.service;

import org.example.messenger.model.User;
import org.example.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.messenger.config.SecurityConfig;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);

        user.setPassword(passwordEncoder.encode(password));
//        user.setPassword(password);

        return userRepository.save(user);
    }
}