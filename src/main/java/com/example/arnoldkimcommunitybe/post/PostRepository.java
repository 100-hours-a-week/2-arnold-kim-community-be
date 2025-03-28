package com.example.arnoldkimcommunitybe.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Override
    @Query("SELECT p FROM PostEntity p JOIN fetch p.user u")
    List<PostEntity> findAll();
}
