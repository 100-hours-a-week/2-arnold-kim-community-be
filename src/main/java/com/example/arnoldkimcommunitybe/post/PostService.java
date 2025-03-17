package com.example.arnoldkimcommunitybe.post;

import com.example.arnoldkimcommunitybe.comment.CommentRepository;
import com.example.arnoldkimcommunitybe.comment.CommentService;
import com.example.arnoldkimcommunitybe.comment.dto.CommentResponseDTO;
import com.example.arnoldkimcommunitybe.component.ImageHandler;
import com.example.arnoldkimcommunitybe.exception.NotFoundException;
import com.example.arnoldkimcommunitybe.post.dto.PostEditRequestDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostListResponseDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostRequestDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostResponseDTO;
import com.example.arnoldkimcommunitybe.postlike.PostLikeRepository;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import com.example.arnoldkimcommunitybe.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageHandler imageHandler;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Transactional
    public void createPost(Session session, PostRequestDTO postRequestDTO, MultipartFile image) throws IOException {
        UserEntity user = userRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("User not Found"));
        String imgUrl = "";

        if(!image.isEmpty()) {
            imgUrl = imageHandler.saveImage(image);
        }

        PostEntity postEntity = PostEntity.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .imageUrl(imgUrl)
                .user(user)
                .build();

        postRepository.save(postEntity);
    }

    public PostResponseDTO getPost(Session session, Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        UserEntity author = postEntity.getUser();
        Boolean liked = postLikeRepository.findByPostIdAndUserId(postId, session.getId()).isPresent();
        List<CommentResponseDTO> commentList = commentService.getComments(postId);
        increaseViews(postEntity);

        return PostResponseDTO.builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .image(postEntity.getImageUrl())
                .author(author.getUsername())
                .authorProfile(author.getProfile())
                .likes(postEntity.getLikeCount())
                .views(postEntity.getViews())
                .createdAt(postEntity.getCreatedAt())
                .liked(liked)
                .comments(commentList)
                .build();
    }

    // TODO
    // redis를 사용해서 관리?
    @Transactional
    protected void increaseViews(PostEntity postEntity) {
        postEntity.incrementViews();
        postRepository.save(postEntity);
    }

    @Transactional
    public void deletePost(Session session, Long postId) throws IOException {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        Long userId = session.getId();
        Long authorId = post.getUser().getId();

        if (!Objects.equals(userId, authorId)) {
            throw new IOException("Unauthorized to delete post");
        }

        postRepository.deleteById(postId);
    }

    public List<PostListResponseDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> PostListResponseDTO.builder()
                        .id(postEntity.getId())
                        .title(postEntity.getTitle())
                        .likes(postEntity.getLikeCount())
                        .views(postEntity.getViews())
                        .createdAt(postEntity.getCreatedAt())
                        .author(postEntity.getUser().getUsername())
                        .authorProfile(postEntity.getUser().getProfile())
                        .comments(commentRepository.countAllByPostId(postEntity.getId()))
                        .build())
                .sorted(Comparator.comparing(PostListResponseDTO::getCreatedAt).reversed())
                .toList();
    }

    @Transactional
    public void editPost(Session session, Long postId, PostEditRequestDTO postEditRequestDTO, MultipartFile file) throws IOException {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        Long userId = session.getId();
        Long authorId = post.getUser().getId();
        if (!Objects.equals(userId, authorId)) {
            throw new IOException("Unauthorized to edit post");
        }

        String imgUrl = imageHandler.saveImage(file);

        post.updateTitle(postEditRequestDTO.getTitle());
        post.updateContent(postEditRequestDTO.getContent());
        post.updateImageUrl(imgUrl);

        postRepository.save(post);
    }
}
