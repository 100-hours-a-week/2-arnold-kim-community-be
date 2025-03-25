package com.example.arnoldkimcommunitybe.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NotBlank
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String email;
    private String password;
    private String passwordCheck;
    private String username;
}
