package com.example.arnoldkimcommunitybe.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NotBlank
@AllArgsConstructor
public class PostRequestDTO {

    public String title;
    public String content;
}
