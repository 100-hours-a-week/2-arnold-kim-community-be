package com.example.arnoldkimcommunitybe.user.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserResponseDTO {
    private String email;
    private String username;
    private String filePath;
}
