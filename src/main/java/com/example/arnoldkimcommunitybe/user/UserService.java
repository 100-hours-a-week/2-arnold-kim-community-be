package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.component.ImageService;
import com.example.arnoldkimcommunitybe.exception.ConfilctException;
import com.example.arnoldkimcommunitybe.exception.NotFoundException;
import com.example.arnoldkimcommunitybe.postlike.PostLikeEntity;
import com.example.arnoldkimcommunitybe.postlike.PostLikeRepository;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.dto.UserPasswordRequestDTO;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import com.example.arnoldkimcommunitybe.user.dto.UserResponseDTO;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void createUser(UserRequestDTO data, MultipartFile file) throws IOException {

        boolean isEmailDuplicated = checkEmail(data.getEmail());
        boolean isUsernameDuplicated = checkUsername(data.getUsername());

        throwSignupException(isEmailDuplicated, isUsernameDuplicated);
        validatePassword(data.getPassword(), bCryptPasswordEncoder.encode(data.getPasswordCheck()));

        if (file == null) {
            throw new BadRequestException("File is required");
        }

        String imgUrl = imageService.saveImage(file);

        userRepository.save(
                UserEntity.builder()
                .username(data.getUsername())
                .password(bCryptPasswordEncoder.encode(data.getPassword()))
                .email(data.getEmail())
                .profile(imgUrl)
                .build());

    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user ->
                    UserResponseDTO.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Session session) {
        UserEntity userEntity = userRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        return UserResponseDTO.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .filePath(userEntity.getProfile())
                .build();
    }

    @Transactional
    public UserResponseDTO changeUserInfo(Session session, UserRequestDTO data, MultipartFile file) throws IOException {
        UserEntity userEntity = userRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("User not found"));

        if (file != null) {
            userEntity.updateProfile(changeUserProfile(file));
        }

        if (data != null) {
            validateUsername(data);
            userEntity.updateUsername(data.getUsername());
        }

        userRepository.save(userEntity);

        return UserResponseDTO.builder()
                .username(userEntity.getUsername())
                .filePath(userEntity.getProfile())
                .build();
    }

    @Transactional
    public void deleteUser(Session session) {
        if (!userRepository.existsById(session.getId())) {
            throw new NotFoundException("User not found");
        }
        deletePostLike(session);

        userRepository.deleteById(session.getId());
    }

    @Transactional
    public void changePassword(Session session, UserPasswordRequestDTO data) {
        UserEntity userEntity = userRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("User not found"));

        validatePassword(data.getPasswordPrev(), userEntity.getPassword());
        validatePassword(data.getPassword(), bCryptPasswordEncoder.encode(data.getPasswordCheck()));

        userEntity.updatePassword(bCryptPasswordEncoder.encode(data.getPassword()));

        userRepository.save(userEntity);
    }

    private boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private String changeUserProfile(MultipartFile file) throws IOException {
        if (file != null) {
            return imageService.saveImage(file);
        }
        return "";
    }

    private void validateUsername(UserRequestDTO data) {
        Map<String, String> errorDetails = new HashMap<>();

        if (checkUsername(data.getUsername())) {
            errorDetails.put("usernameError", "*중복된 닉네임입니다.");
            throw new ConfilctException("Conflict", errorDetails);
        }
    }

    private void deletePostLike(Session session) {
        List<PostLikeEntity> postLikeEntities = postLikeRepository.findAllByUserId(session.getId());
        postLikeEntities.forEach(postLikeEntity -> {
            postLikeEntity.getPost().decrementLikes();
            postLikeRepository.save(postLikeEntity);
        });
    }

    private void validatePassword(String password, String passwordCheck) {
        if (! bCryptPasswordEncoder.matches(password, passwordCheck)) {
            throw new ConfilctException("password not matches", null);
        }
    }

    private void throwSignupException(boolean isEmailDuplicated, boolean isUsernameDuplicated) {
        Map<String, String> errorDetails = new HashMap<>();

        if (isEmailDuplicated || isUsernameDuplicated) {
            if (isEmailDuplicated){
                errorDetails.put("email", "*중복된 이메일입니다.");
            } else {
                errorDetails.put("email", "");
            }
            if (isUsernameDuplicated){
                errorDetails.put("username", "*중복된 닉네임입니다.");
            } else {
                errorDetails.put("username", "");
            }
            throw new ConfilctException("Conflict", errorDetails);
        }
    }
}
