package com.example.arnoldkimcommunitybe.postlike;

import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    Optional<PostLikeEntity> findByPostIdAndUserId(Long post_id, Long user_id);

    @Query("SELECT pl from PostLikeEntity pl join fetch pl.post where pl.user.id = :userId")
    List<PostLikeEntity> findAllByUserId(@Param("userId") Long userId);
}
