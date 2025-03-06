package com.example.arnoldkimcommunitybe.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDTO {
    private String email;
    private String password;
    private String passwordCheck;
    private String username;
    private String profile;
}
