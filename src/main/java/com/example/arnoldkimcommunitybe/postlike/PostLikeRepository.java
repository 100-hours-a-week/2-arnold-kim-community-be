package com.example.arnoldkimcommunitybe.postlike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    Optional<PostLikeEntity> findByPostIdAndUserId(Long post_id, Long user_id);
    long countByPostId(Long post_id);
}
