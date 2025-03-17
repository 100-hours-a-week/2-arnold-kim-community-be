package com.example.arnoldkimcommunitybe.comment;

import com.example.arnoldkimcommunitybe.comment.dto.CommentRequestDTO;
import com.example.arnoldkimcommunitybe.comment.dto.CommentResponseDTO;
import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.response.StatusResponse;
import com.example.arnoldkimcommunitybe.security.CurrentSession;
import com.example.arnoldkimcommunitybe.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public StatusResponse createComment(@CurrentSession Session session,
                                        @RequestBody CommentRequestDTO commentRequestDTO,
                                        @PathVariable Long postId) {
        commentService.createComment(commentRequestDTO, session, postId);

        return StatusResponse.of(201, "Comment created");
    }

    @GetMapping("/{postId}")
    public DataResponse<List<CommentResponseDTO>> getComment(@PathVariable Long postId) {
        List<CommentResponseDTO> commentResponseDTO = commentService.getComments(postId);

        return DataResponse.of(commentResponseDTO);
    }
}
