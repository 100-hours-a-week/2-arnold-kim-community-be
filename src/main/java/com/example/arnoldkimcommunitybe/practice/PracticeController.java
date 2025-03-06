package com.example.arnoldkimcommunitybe.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/practice")
@RequiredArgsConstructor
public class PracticeController {

    private final PracticeService practiceService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/practice")
    public String printPracticeResponseDTO() {
        return practiceService.practiceResponse("안녕하세요");
    }

    @GetMapping()
    public List<Practice> printPracticeUserResponseDTO() {
        return practiceService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public Practice printPracticeUserResponseDTO(@PathVariable Integer userId) {
        practiceService.makePracticeEntity();
        PracticeEntity practiceEntity = practiceService.getPracticeEntity(userId);
        return Practice.builder()
                .email(practiceEntity.getEmail())
                .name(practiceEntity.getName())
                .build();
    }

}
