package com.example.arnoldkimcommunitybe.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@NotBlank
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPasswordRequestDTO {
    private String passwordPrev;
    private String password;
    private String passwordCheck;
}
