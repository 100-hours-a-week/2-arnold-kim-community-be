package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.response.StatusResponse;
import com.example.arnoldkimcommunitybe.security.CurrentSession;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import com.example.arnoldkimcommunitybe.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public DataResponse<UserResponseDTO> user(@CurrentSession Session session) {
        UserResponseDTO userResponseDTO = userService.getUser(session);
        return DataResponse.of(userResponseDTO);
    }

    @PostMapping("/signup")
    public StatusResponse signup(@RequestPart UserRequestDTO userRequestDTO,
                                 @RequestPart MultipartFile file) throws IOException {
        userService.createUser(userRequestDTO, file);
        return StatusResponse.of(201, "register_success");
    }

    @GetMapping("/info")
    public DataResponse<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> userList = userService.getAllUsers();
        return DataResponse.of(userList);
    }

    @PatchMapping("/")
    public StatusResponse changeUsername(@CurrentSession Session session,
                                         @RequestPart UserRequestDTO userRequestDTO,
                                         @RequestPart(required = false) MultipartFile file) throws IOException {
        userService.changeUsername(session, userRequestDTO, file);
        return StatusResponse.of(200, "change_success");
    }
}
