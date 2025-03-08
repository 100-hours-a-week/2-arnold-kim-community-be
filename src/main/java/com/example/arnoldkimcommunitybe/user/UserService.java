package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.component.ImageHandler;
import com.example.arnoldkimcommunitybe.exception.ConfilctException;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageHandler imageHandler;

    public String createUser(UserRequestDTO data, MultipartFile file) throws IOException {
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
                .password(data.getPassword())
                .email(data.getEmail())
                .profile(imgUrl)
                .build());

        return imgUrl;
    }

    private boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
