package com.example.arnoldkimcommunitybe.user.controller;

import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.UserController;
import com.example.arnoldkimcommunitybe.user.UserService;
import com.example.arnoldkimcommunitybe.user.config.TestSecurityConfig;
import com.example.arnoldkimcommunitybe.util.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class})
@ContextConfiguration(classes = TestSecurityConfig.class)
@WithMockUser
public class UserInfoControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    private Session session;
    private MockMultipartFile image;

    @BeforeEach
    void setUp() {
        this.image = new MockMultipartFile("image",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes());
        this.session = (Session) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private static Stream<Arguments> invalidChangeUserProfile() {
        return Stream.of(
                Arguments.of(new MockMultipartFile("image",
                        "test.pdf",
                        "image/jpeg",
                        "dummy image content".getBytes()), "changedName"),
                Arguments.of(new MockMultipartFile("image",
                        "test.jpg",
                        "image/gif",
                        "dummy image content".getBytes()), "changedName")
        );
    }
    @DisplayName("회원 프로필 사진 변경하기 - 실패")
    @ParameterizedTest
    @MethodSource("invalidChangeUserProfile")
    void changeUserProfileFail(MockMultipartFile invalidFile, String changedName) throws Exception {
        // when then
        mvc.perform(multipart(HttpMethod.PATCH,"/users/")
                        .file(invalidFile)
                        .param("changedName", changedName)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}
