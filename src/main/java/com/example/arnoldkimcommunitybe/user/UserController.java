package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.response.StatusResponse;
import com.example.arnoldkimcommunitybe.security.CurrentSession;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.dto.UserPasswordRequestDTO;
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

    @PatchMapping("/password")
    public StatusResponse changePassword(@CurrentSession Session session,
                                         @RequestBody UserPasswordRequestDTO data){
        userService.changePassword(session, data);
        return StatusResponse.of(200, "change_password_success");
    }

    @GetMapping("/")
    public DataResponse<UserResponseDTO> user(@CurrentSession Session session) {

        UserResponseDTO userResponseDTO = userService.getUser(session);
        return DataResponse.of(userResponseDTO);
    }

    @PatchMapping("/")
    public DataResponse<UserResponseDTO> changeUserInfo(@CurrentSession Session session,
                                         @RequestPart UserRequestDTO userRequestDTO,
                                         @RequestPart(required = false) MultipartFile file) throws IOException {
        UserResponseDTO userResponseDTO = userService.changeUserInfo(session, userRequestDTO, file);
        return DataResponse.of(userResponseDTO);
    }

    @DeleteMapping("/")
    public StatusResponse deleteUser(@CurrentSession Session session) {
        userService.deleteUser(session);
        return StatusResponse.of(200, "delete_success");
    }
}
