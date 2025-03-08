package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.response.StatusResponse;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public String user(@PathVariable("userId") long userId) {
        return "hello " + userId;
    }

    @PostMapping("/signup")
    public StatusResponse signup(@RequestPart UserRequestDTO userRequestDTO,
                                 @RequestPart MultipartFile file) throws IOException {
        return StatusResponse.of(201, "register_success");
    }
}
