package com.example.arnoldkimcommunitybe.comment;

import com.example.arnoldkimcommunitybe.post.PostEntity;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private PostEntity post;

    @Builder
    public CommentEntity(String content, LocalDateTime createdAt, UserEntity user, PostEntity post) {
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }
}
