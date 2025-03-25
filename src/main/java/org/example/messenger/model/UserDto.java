package org.example.messenger.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

@Setter
public class UserDto {

    @NotBlank(message = "Имя должно быть не пустое")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    public UserDto() {
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank(message = "Имя должно быть не пустое") String getUsername() {
        return username;
    }

    public @NotBlank(message = "Пароль не должен быть пустым") String getPassword() {
        return password;
    }

}
