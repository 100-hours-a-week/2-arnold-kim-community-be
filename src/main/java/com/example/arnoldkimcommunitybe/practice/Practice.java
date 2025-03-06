package com.example.arnoldkimcommunitybe.practice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Practice {
    private int id;
    private String name;
    private String email;
}
