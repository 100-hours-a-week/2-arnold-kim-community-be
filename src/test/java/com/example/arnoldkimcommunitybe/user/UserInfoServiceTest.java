package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.exception.NotFoundException;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.dto.UserResponseDTO;
import com.example.arnoldkimcommunitybe.util.WithMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@WithMockUser
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
        // given
        String email = session.getUsername();
        Long id = session.getId();

        // mocking
        UserEntity user = UserEntity.builder()
                .email(email)
                .profile("path/profile")
                .password("1234")
                .username("test")
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // when
        UserResponseDTO result = userService.getUser(session);

        // then
        UserResponseDTO response = UserResponseDTO.builder()
                .email(email)
                .username("test")
                .filePath("path/profile")
                .build();

        assertThat(result).isEqualTo(response);
    }

    @DisplayName("회원 정보 불러오기 - 실패 : 없는 유저")
    @Test
    void getUserNotFound() {
        // given
        Long id = session.getId();

        // mocking
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // when then
        NotFoundException e = assertThrows(NotFoundException.class, () -> userService.getUser(session));
        assertThat(e.getMessage()).isEqualTo("User not found");
    }
}
