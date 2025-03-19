package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class UserInfoServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private Session session;


    @BeforeEach
    void setUp() {
        this.session = (Session) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @DisplayName("회원정보 조회 - 성공")
    @Test
    void getUserSuccess() {
        String email = session.getUsername();

        UserEntity user = UserEntity.builder()
                .email(email)
                .profile("path/profile")
                .password("1234")
                .username("test")
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        UserResponseDTO result = userService.getUser(session);

        // then
        UserResponseDTO response = UserResponseDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .filePath(user.getProfile())
                .build();

        assertThat(result).isEqualTo(response);
    }
}
