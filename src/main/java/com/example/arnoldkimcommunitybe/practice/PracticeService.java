package com.example.arnoldkimcommunitybe.practice;

import org.springframework.stereotype.Service;

@Service
public class PracticeService {
    public String practiceResponse(String content) {
        return "Hello" + content;
    }

}
