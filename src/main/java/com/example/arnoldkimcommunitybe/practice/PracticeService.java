package com.example.arnoldkimcommunitybe.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticeService {
    private final PracticeRepository practiceRepository;

    public String practiceResponse(String content) {
        return "Hello" + content;
    }

    public List<Practice> getAllUsers() {
        return practiceRepository.findAllUsers();
    }

}
