package com.example.arnoldkimcommunitybe.post.dto;

import com.example.arnoldkimcommunitybe.post.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class PostListResponseDTO {
    private Long id;
    private String title;
    private Long likes;
    private Long views;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    private String authorProfile;
    private String author;
    private Long comments;
}
