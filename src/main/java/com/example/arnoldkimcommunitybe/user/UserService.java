package com.example.arnoldkimcommunitybe.user;

import com.example.arnoldkimcommunitybe.exception.ConfilctException;
import com.example.arnoldkimcommunitybe.user.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRequestDTO data) {
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


        userRepository.save(
                UserEntity.builder()
                .username(data.getUsername())
                .password(data.getPassword())
                .email(data.getEmail())
                .profile(data.getProfile())
                .build());
    }

    private boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
