package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.comment.CommentEntity;
import com.example.arnoldkimcommunitybe.post.PostEntity;
import com.example.arnoldkimcommunitybe.postlike.PostLikeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String username;
    private String profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostLikeEntity> postLikes;

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    @Builder
    public UserEntity(String email, String password, String username, String profile) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.profile = profile;

        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.postLikes = new ArrayList<>();
    }
}
