package org.example.messenger.controller;

import org.example.messenger.model.User;
import org.example.messenger.model.UserDto;
import org.example.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;


    //Регистрация пользователя
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok(user);
    }

}