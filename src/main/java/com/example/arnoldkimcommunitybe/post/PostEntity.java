package com.example.arnoldkimcommunitybe.post;

import com.example.arnoldkimcommunitybe.postlike.PostLikeEntity;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Long views;
    private LocalDateTime createdAt;

    @ManyToOne
    private UserEntity user;

    @OneToMany
    private List<PostLikeEntity> likes;

    @Builder
    public PostEntity(String title, String content, String imageUrl, Long views, LocalDateTime createdAt, UserEntity user) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.views = views;
        this.createdAt = createdAt;
        this.user = user;

        this.likes = new ArrayList<>();

    }

    public void incrementViews() {
        this.views++;
    }
}
