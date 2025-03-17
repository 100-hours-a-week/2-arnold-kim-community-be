package com.example.arnoldkimcommunitybe.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private Long commentAuthorId;
    private String commentAuthor;
    private String commentContent;
    private String commentAuthorProfile;
    private LocalDateTime commentDate;
}
