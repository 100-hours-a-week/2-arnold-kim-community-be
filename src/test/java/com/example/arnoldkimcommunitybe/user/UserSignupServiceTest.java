package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.exception.ConfilctException;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserSignupServiceTest {
    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @DisplayName("회원가입 - 실패")
    @Test
    void createUserFail() {
        // given
        String email = "test@test.com";
        String password = "Test1234!";
        String passwordCheck = "Test1234!";
        String username = "test";
        MultipartFile image = new MockMultipartFile("image",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes());

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email(email)
                .password(password)
                .passwordCheck(passwordCheck)
                .username(username)
                .build();

        // mocking
        when(userRepository.existsByEmail(userRequestDTO.getEmail())).thenReturn(true);
        when(userRepository.existsByUsername(userRequestDTO.getUsername())).thenReturn(true);

        // when then
        assertThrows(ConfilctException.class, () -> userService.createUser(userRequestDTO, image));
    }

    @DisplayName("회원가입 - 성공")
    @Test
    void createUserSuccess() throws IOException {
        // given
        String email = "test@test.com";
        String password = "Test1234!";
        String passwordCheck = "Test1234!";
        String username = "test";
        MultipartFile image = new MockMultipartFile("image",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes());

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email(email)
                .password(password)
                .passwordCheck(passwordCheck)
                .username(username)
                .build();

        // mocking
        when(userRepository.existsByEmail(userRequestDTO.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(userRequestDTO.getUsername())).thenReturn(false);

        // when
        userService.createUser(userRequestDTO, image);

        // then
        verify(userRepository, times(1)).save(any(UserEntity.class));

    }
}