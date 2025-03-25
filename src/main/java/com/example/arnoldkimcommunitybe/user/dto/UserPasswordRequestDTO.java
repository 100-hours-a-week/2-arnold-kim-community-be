package com.example.arnoldkimcommunitybe.user.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPasswordRequestDTO {
    private String password;
    private String passwordCheck;
}
