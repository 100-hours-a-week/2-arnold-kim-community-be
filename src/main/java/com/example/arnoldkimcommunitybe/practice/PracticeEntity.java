package com.example.arnoldkimcommunitybe.practice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PracticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    private String name;
    private String email;

    @Builder
    public PracticeEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
