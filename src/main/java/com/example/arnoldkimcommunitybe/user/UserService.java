package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.component.ImageHandler;
import com.example.arnoldkimcommunitybe.exception.ConfilctException;
import com.example.arnoldkimcommunitybe.exception.NotFoundException;
import com.example.arnoldkimcommunitybe.security.Session;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import com.example.arnoldkimcommunitybe.user.dto.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final ImageHandler imageHandler;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createUser(UserRequestDTO data, MultipartFile file) throws IOException {
        Map<String, String> errorDetails = new HashMap<>();
        // email, 닉네임 중복 체크
        boolean isEmailDuplicated = checkEmail(data.getEmail());
        boolean isUsernameDuplicated = checkUsername(data.getUsername());

        if (isEmailDuplicated || isUsernameDuplicated) {
            if (checkEmail(data.getEmail())){
                errorDetails.put("email", "*중복된 이메일입니다.");
            } else {
                errorDetails.put("email", "");
            }
            if (checkUsername(data.getUsername())){
                errorDetails.put("username", "*중복된 닉네임입니다.");
            } else {
                errorDetails.put("username", "");
            }
            throw new ConfilctException("Conflict", errorDetails);
        }

        String imgUrl = imageHandler.saveImage(file);

        userRepository.save(
                UserEntity.builder()
                .username(data.getUsername())
                .password(bCryptPasswordEncoder.encode(data.getPassword()))
                .email(data.getEmail())
                .profile(imgUrl)
                .build());

    }

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

    public UserResponseDTO getUser(Session session) {
        UserEntity userEntity = userRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        return UserResponseDTO.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .filePath(userEntity.getProfile())
                .build();
    }

    @Transactional
    public void changeUsername(Session session, UserRequestDTO data, MultipartFile file) throws IOException {
        UserEntity userEntity = userRepository.findById(session.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        Map<String, String> errorDetails = new HashMap<>();

        if (checkUsername(data.getUsername())) {
            errorDetails.put("usernameError", "*중복된 닉네임입니다.");
            throw new ConfilctException("Conflict", errorDetails);
        }
        System.out.println(file == null);
        if (file != null) {
            String imgUrl = imageHandler.saveImage(file);
            userEntity.updateProfile(imgUrl);
        }

        userEntity.updateUsername(data.getUsername());

        userRepository.save(userEntity);
    }

    public void deleteUser(Session session) {
        userRepository.deleteById(session.getId());
    }

    private boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
