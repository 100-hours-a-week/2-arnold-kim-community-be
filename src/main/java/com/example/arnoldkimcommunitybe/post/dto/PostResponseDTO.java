package com.example.arnoldkimcommunitybe.post.dto;

import com.example.arnoldkimcommunitybe.comment.dto.CommentResponseDTO;
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
    private Integer likes;
    private Long views;
    private LocalDateTime createdAt;
    private List<CommentResponseDTO> comments;
}
