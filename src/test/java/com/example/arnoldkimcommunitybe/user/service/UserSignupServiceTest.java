package com.example.arnoldkimcommunitybe.user.service;

import com.example.arnoldkimcommunitybe.exception.ConfilctException;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import com.example.arnoldkimcommunitybe.user.UserRepository;
import com.example.arnoldkimcommunitybe.user.UserService;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserSignupServiceTest {
    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static Stream<Arguments> invalidSignup() {
        return Stream.of(
                Arguments.of(true, true, "test@test.com", "Test1234!", "Test1234!", "sayho", new MockMultipartFile("image",
                        "test.jpg",
                        "image/jpeg",
                        "dummy image content".getBytes())),
                Arguments.of(false, true, "test@test.com", "Test1234!", "Test1234!", "sayho", new MockMultipartFile("image",
                        "test.jpg",
                        "image/jpeg",
                        "dummy image content".getBytes())),
                Arguments.of(true, false, "test@test.com", "Test1234!", "Test1234!", "sayho", new MockMultipartFile("image",
                        "test.jpg",
                        "image/jpeg",
                        "dummy image content".getBytes())),
                Arguments.of(false, false, "test@test.com", "Test1234!", "Test12345!", "sayho", new MockMultipartFile("image",
                        "test.jpg",
                        "image/jpeg",
                        "dummy image content".getBytes())),
                Arguments.of(false, false, "test@test.com", "Test1234!", "Test1234!", "sayho", null)
        );
    }

    @DisplayName("회원가입 - 실패")
    @ParameterizedTest
    @MethodSource("invalidSignup")
    void createUserFail(boolean isEmailExist, boolean isUsernameExists, String email, String password, String passwordCheck, String username, MockMultipartFile image) {
        // given
        UserRequestDTO request = UserRequestDTO.builder()
                .email(email)
                .password(password)
                .passwordCheck(passwordCheck)
                .username(username)
                .build();

        // mocking
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(isEmailExist);
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(isUsernameExists);

        // when then
        if (image != null) {
            assertThrows(ConfilctException.class, () -> userService.createUser(request, image));
        } else {
            assertThrows(BadRequestException.class, () -> userService.createUser(request, image));
        }

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