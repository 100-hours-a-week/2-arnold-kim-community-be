package com.example.arnoldkimcommunitybe.post;

import com.example.arnoldkimcommunitybe.post.dto.PostRequestDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostResponseDTO;
import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.response.StatusResponse;
import com.example.arnoldkimcommunitybe.security.CurrentSession;
import com.example.arnoldkimcommunitybe.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    public StatusResponse createPost(@CurrentSession Session session,
                                     @RequestPart PostRequestDTO postRequestDTO,
                                     @RequestPart(required = false) MultipartFile image) throws IOException {
        postService.createPost(session, postRequestDTO, image);

        return StatusResponse.of(201, "post_success");
    }

    @GetMapping("/{postId}")
    public DataResponse<PostResponseDTO> getPost(@CurrentSession Session session, @PathVariable Long postId) {
        PostResponseDTO postResponseDTO = postService.getPost(postId);

        return DataResponse.of(postResponseDTO);
    }
}
