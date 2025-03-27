package com.example.arnoldkimcommunitybe.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final JPAPracticeRepository jpapracticeRepository;

    public String practiceResponse(String content) {
        return "Hello" + content;
    }

    @Transactional(readOnly = true)
    public List<Practice> getAllUsers() {
        return practiceRepository.findAllUsers();
    }

    public void makePracticeEntity() {
        jpapracticeRepository.save(
                PracticeEntity.builder()
                .email("email@email.com")
                .name("name")
                .build());
    }

    public PracticeEntity getPracticeEntity(int id) {
        return jpapracticeRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

}
