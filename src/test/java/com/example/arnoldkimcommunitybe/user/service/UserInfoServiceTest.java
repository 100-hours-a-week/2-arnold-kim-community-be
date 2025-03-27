package com.example.arnoldkimcommunitybe.user.service;

import com.example.arnoldkimcommunitybe.component.ImageService;
import com.example.arnoldkimcommunitybe.exception.ConfilctException;
import com.example.arnoldkimcommunitybe.exception.NotFoundException;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.UserEntity;
import com.example.arnoldkimcommunitybe.user.UserRepository;
import com.example.arnoldkimcommunitybe.user.UserService;
import com.example.arnoldkimcommunitybe.user.dto.UserPasswordRequestDTO;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import com.example.arnoldkimcommunitybe.user.dto.UserResponseDTO;
import com.example.arnoldkimcommunitybe.util.WithMockUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@WithMockUser
public class UserInfoServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @DisplayName("회원 이름 변경하기 - 성공")
    @Test
    void updateUsernameSuccess() throws IOException {
        // given
        Long id = session.getId();

        UserRequestDTO request = UserRequestDTO.builder()
                .username("changedName")
                .build();

        // mocking
        UserEntity user = UserEntity.builder()
                .profile("path/profile")
                .password("1234")
                .username("test")
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // when
        UserResponseDTO result = userService.changeUserInfo(session, request, null);

        // then
        UserResponseDTO response = UserResponseDTO.builder()
                .username("changedName")
                .filePath("")
                .build();

        assertThat(result).isEqualTo(response);
    }

    @DisplayName("회원 이름 변경하기 - 실패 : 중복된 닉네임")
    @Test
    void updateUsernameFailure() {
        // given
        Long id = session.getId();
        UserRequestDTO request = UserRequestDTO.builder()
                .username("changedName")
                .build();

        // mocking
        UserEntity user = UserEntity.builder()
                .profile("path/profile")
                .password("1234")
                .username("test")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("changedName")).thenReturn(true);
        Assertions.assertThrows(ConfilctException.class, () -> userService.changeUserInfo(session, request, null));

    }

    @DisplayName("회원 프로필사진 변경하기 - 성공")
    @Test
    void updateUserProfileSuccess() throws IOException {
        // given
        Long id = session.getId();

        MultipartFile image = new MockMultipartFile("image",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes());

        // mocking
        UserEntity user = UserEntity.builder()
                .profile("path/profile")
                .password("1234")
                .username("test")
                .build();
        when(imageService.saveImage(image)).thenReturn("path/profile");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // when
        UserResponseDTO result = userService.changeUserInfo(session, null, image);

        // then
        UserResponseDTO response = UserResponseDTO.builder()
                .filePath("path/profile")
                .username("test")
                .build();
        assertThat(result).isEqualTo(response);
    }

    @DisplayName("회원 탈퇴 - 실패")
    @Test
    void userWithdrawFailure() {
        // given

        // when then
        Assertions.assertThrows(NotFoundException.class, () -> {userService.deleteUser(session);});
    }

    @DisplayName("회원 탈퇴 - 성공")
    @Test
    void userWithdrawSuccess() {

        // given
        Long id = session.getId();

        // mocking
        when(userRepository.existsById(id)).thenReturn(true);

        // when
        userService.deleteUser(session);

        // then
        verify(userRepository, times(1)).deleteById(id);
    }

    private static Stream<Arguments> invalidChangePassword() {
        return Stream.of(
                Arguments.of("Test1234!", "Test1234!!", "Test1234!!"),
                Arguments.of("Test1111!", "Test1234!!", "Test124!!"),
                Arguments.of("Test1111!", "Test123!!", "Test1234!!")
        );
    }

    @DisplayName("비밀번호 변경 - 실패")
    @ParameterizedTest
    @MethodSource("invalidChangePassword")
    void changeUserPasswordFailure(String passwordPrev, String password, String passwordCheck) {
        // given
        Long id = session.getId();
        UserPasswordRequestDTO request = UserPasswordRequestDTO.builder()
                .password(password)
                .passwordCheck(passwordCheck)
                .passwordPrev(passwordPrev)
                .build();

        // mocking
        UserEntity user = UserEntity.builder()
                .email(session.getUsername())
                .password(bCryptPasswordEncoder.encode("Test1111!"))
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // when then
        assertThrows(ConfilctException.class, () -> userService.changePassword(session, request));

    }

    @DisplayName("비밀번호 변경 - 성공")
    @Test
    void changeUserPasswordSuccess() {
        // given
        Long id = session.getId();
        String changePassword = "Test12345!!";
        String changePasswordCheck = "Test12345!!";
        String prevPassword = "Test1111!";
        UserPasswordRequestDTO request = UserPasswordRequestDTO.builder()
                .password(changePassword)
                .passwordCheck(changePasswordCheck)
                .passwordPrev(prevPassword)
                .build();

        // mocking
        UserEntity user = UserEntity.builder()
                .email(session.getUsername())
                .password(bCryptPasswordEncoder.encode(prevPassword))
                .build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsById(id)).thenReturn(true);

        // when
        userService.changePassword(session, request);

        // then
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any(UserEntity.class));


    }

}
