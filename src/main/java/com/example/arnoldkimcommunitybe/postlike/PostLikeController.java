package com.example.arnoldkimcommunitybe.postlike;

import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.response.StatusResponse;
import com.example.arnoldkimcommunitybe.security.CurrentSession;
import com.example.arnoldkimcommunitybe.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/like")
    public StatusResponse like(@PathVariable Long postId, @CurrentSession Session session) {
        postLikeService.postLike(session, postId);

        return StatusResponse.of(200, "Liked");
    }

    @DeleteMapping("/{postId}/like")
    public StatusResponse unlike(@PathVariable Long postId, @CurrentSession Session session) {
        postLikeService.postUnlike(session, postId);

        return StatusResponse.of(204, "Unliked");
    }
}
