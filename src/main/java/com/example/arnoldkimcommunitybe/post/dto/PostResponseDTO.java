package com.example.arnoldkimcommunitybe.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDTO {
    private String title;
    private String content;
    private String image;
    private String author;
    private String authorProfile;
    private Integer likes;
    private Long views;
    private LocalDateTime createdAt;
}
