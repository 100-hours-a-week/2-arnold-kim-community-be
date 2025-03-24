package com.example.arnoldkimcommunitybe.post;

import com.example.arnoldkimcommunitybe.post.dto.PostEditRequestDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostListResponseDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostRequestDTO;
import com.example.arnoldkimcommunitybe.post.dto.PostResponseDTO;
import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.response.StatusResponse;
import com.example.arnoldkimcommunitybe.security.CurrentSession;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.util.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    public StatusResponse createPost(@CurrentSession Session session,
                                     @RequestPart PostRequestDTO postRequestDTO,
                                     @ImageFile @RequestPart(required = false) MultipartFile image) throws IOException {
        postService.createPost(session, postRequestDTO, image);

        return StatusResponse.of(201, "post_success");
    }

    @GetMapping("/")
    public DataResponse<List<PostListResponseDTO>> getAllPosts() {
        return DataResponse.of(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public DataResponse<PostResponseDTO> getPost(@CurrentSession Session session, @PathVariable Long postId) {
        PostResponseDTO postResponseDTO = postService.getPost(session, postId);

        return DataResponse.of(postResponseDTO);
    }

    @DeleteMapping("/{postId}")
    public StatusResponse deletePost(@CurrentSession Session session, @PathVariable Long postId) throws IOException {
        postService.deletePost(session, postId);

        return StatusResponse.of(204, "post_delete_success");
    }

    @PatchMapping("/{postId}")
    public StatusResponse editPost(@CurrentSession Session session,
                                   @PathVariable Long postId,
                                   @ImageFile @RequestPart(required = false) MultipartFile image,
                                   @RequestPart PostEditRequestDTO postEditRequestDTO) throws IOException {

        postService.editPost(session, postId, postEditRequestDTO, image);
        return StatusResponse.of(200, "post_edit_success");
    }
}
