package com.example.arnoldkimcommunitybe.post;

import com.example.arnoldkimcommunitybe.comment.CommentEntity;
import com.example.arnoldkimcommunitybe.global.BaseEntity;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Long views;
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<PostLikeEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();


    public void incrementViews() {
        this.views++;
    }
    public void incrementLikes() { this.likeCount++; }
    public void decrementLikes() { this.likeCount--; }
    public void updateTitle(String title) {this.title = title;}
    public void updateContent(String content) {this.content = content;}
    public void updateImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

}
