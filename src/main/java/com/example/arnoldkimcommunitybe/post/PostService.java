package com.example.arnoldkimcommunitybe.post;

import com.example.arnoldkimcommunitybe.component.ImageHandler;
import com.example.arnoldkimcommunitybe.exception.NotFoundException;
import com.example.arnoldkimcommunitybe.post.dto.PostRequestDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostResponseDTO;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import com.example.arnoldkimcommunitybe.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageHandler imageHandler;

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
                .views(0L)
                .imageUrl(imgUrl)
                .user(user)
                .build();

        postRepository.save(postEntity);
    }

    public PostResponseDTO getPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        UserEntity user = postEntity.getUser();
        increaseViews(postEntity);

        return PostResponseDTO.builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .image(postEntity.getImageUrl())
                .author(user.getUsername())
                .authorProfile(user.getProfile())
                .likes(postEntity.getLikes().size())
                .views(postEntity.getViews())
                .createdAt(postEntity.getCreatedAt())
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
}
