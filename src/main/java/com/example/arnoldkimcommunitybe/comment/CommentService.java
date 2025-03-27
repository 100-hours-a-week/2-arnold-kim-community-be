package com.example.arnoldkimcommunitybe.comment;

import com.example.arnoldkimcommunitybe.comment.dto.CommentRequestDTO;
import com.example.arnoldkimcommunitybe.comment.dto.CommentResponseDTO;
import com.example.arnoldkimcommunitybe.exception.IOException;
import com.example.arnoldkimcommunitybe.exception.NotFoundException;
import com.example.arnoldkimcommunitybe.post.PostEntity;
import com.example.arnoldkimcommunitybe.post.PostRepository;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import com.example.arnoldkimcommunitybe.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createComment(CommentRequestDTO commentRequestDTO, Session session, Long postId) {
        UserEntity user = userRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));

        commentRepository.save(CommentEntity.builder()
                .user(user)
                .post(post)
                .content(commentRequestDTO.getContent())
                .build());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getComments(Long postId) {
        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(commentEntity -> CommentResponseDTO.builder()
                        .id(commentEntity.getId())
                        .author(commentEntity.getUser().getUsername())
                        .authorId(commentEntity.getUser().getId())
                        .content(commentEntity.getContent())
                        .createdAt(commentEntity.getCreatedAt())
                        .authorProfile(commentEntity.getUser().getProfile())
                        .build())
                .sorted(Comparator.comparing(CommentResponseDTO::getCreatedAt).reversed())
                .toList();
    }

    @Transactional
    public void deleteComment(Long commentId, Session session) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found"));
        if (!comment.getUser().getId().equals(session.getId())) {
            throw new IOException("Unauthorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void updateComment(Long commentId, CommentRequestDTO commentRequestDTO, Session session) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found"));
        if (!comment.getUser().getId().equals(session.getId())) {
            throw new IOException("Unauthorized to update this comment");
        }

        comment.updateContent(commentRequestDTO.getContent());
        commentRepository.save(comment);
    }
}
