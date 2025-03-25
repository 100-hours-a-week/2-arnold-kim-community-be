package com.example.arnoldkimcommunitybe.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@NotNull
public class PostEditRequestDTO {
    private String title;
    private String content;
}
