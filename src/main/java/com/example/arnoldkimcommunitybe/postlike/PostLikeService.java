package com.example.arnoldkimcommunitybe.postlike;

import com.example.arnoldkimcommunitybe.post.PostEntity;
import com.example.arnoldkimcommunitybe.post.PostRepository;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import com.example.arnoldkimcommunitybe.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void postLike(Session session, Long postId) {
        UserEntity user = userRepository.findById(session.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.incrementLikes();
        postRepository.save(post);
        postLikeRepository.save(PostLikeEntity.builder()
                        .post(post)
                        .user(user)
                        .build());
    }

    @Transactional
    public void postUnlike(Session session, Long postId) {
        PostLikeEntity postLike = postLikeRepository.findByPostIdAndUserId(session.getId(), postId).orElseThrow(() -> new RuntimeException("Post Like not found"));
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.decrementLikes();
        postRepository.save(post);
        postLikeRepository.delete(postLike);
    }

}

