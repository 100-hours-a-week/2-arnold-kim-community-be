package com.example.arnoldkimcommunitybe.postlike;

import com.example.arnoldkimcommunitybe.post.PostEntity;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PostEntity post;

    @ManyToOne
    private UserEntity user;

    @Builder
    public PostLikeEntity(PostEntity post, UserEntity user) {
        this.post = post;
        this.user = user;
    }
}
