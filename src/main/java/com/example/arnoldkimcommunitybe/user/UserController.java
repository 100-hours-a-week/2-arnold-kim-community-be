package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.response.DataResponse;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public void signup(@RequestBody UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
    }
}
