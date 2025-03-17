package com.example.arnoldkimcommunitybe.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private Long authorId;
    private String author;
    private String content;
    private String authorProfile;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}
