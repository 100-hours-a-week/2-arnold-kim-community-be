package com.example.arnoldkimcommunitybe.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordRequestDTO {
    private String password;
    private String passwordCheck;
}
