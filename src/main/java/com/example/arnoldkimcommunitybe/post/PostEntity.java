package com.example.arnoldkimcommunitybe.post;

import com.example.arnoldkimcommunitybe.comment.CommentEntity;
import com.example.arnoldkimcommunitybe.postlike.PostLikeEntity;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @ManyToOne
    private UserEntity user;

    @OneToMany
    private List<PostLikeEntity> likes;

    @OneToMany
    private List<CommentEntity> comments;

    @Builder
    public PostEntity(String title, String content, String imageUrl, Long views, LocalDateTime createdAt, UserEntity user) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.views = 0L;
        this.createdAt = createdAt;
        this.user = user;
        this.likeCount = 0L;

        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void incrementViews() {
        this.views++;
    }
    public void incrementLikes() { this.likeCount++; }
    public void decrementLikes() { this.likeCount--; }
    public void updateTitle(String title) {this.title = title;}
    public void updateContent(String content) {this.content = content;}
    public void updateImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

}
