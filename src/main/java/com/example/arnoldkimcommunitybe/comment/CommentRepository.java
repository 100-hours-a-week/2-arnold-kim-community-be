package com.example.arnoldkimcommunitybe.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c JOIN fetch c.user where c.post.id = :postId")
    List<CommentEntity> findAllByPostId(@Param("postId") Long postId);

    long countAllByPostId(Long postId);
}
