package com.example.arnoldkimcommunitybe.comment;

import com.example.arnoldkimcommunitybe.post.PostEntity;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne
    private PostEntity post;

    public void updateContent(String content) {
        this.content = content;
    }
}
