package org.example.messenger.controller;


import org.example.messenger.model.User;
import org.example.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Получение всех пользователей
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
