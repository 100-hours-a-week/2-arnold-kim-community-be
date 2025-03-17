package com.example.arnoldkimcommunitybe.post.dto;

import com.example.arnoldkimcommunitybe.comment.dto.CommentResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDTO {
    private String title;
    private String content;
    private String image;
    private String author;
    private String authorProfile;
    private Long likes;
    private Long views;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    private List<CommentResponseDTO> comments;
    private Boolean liked;
}
