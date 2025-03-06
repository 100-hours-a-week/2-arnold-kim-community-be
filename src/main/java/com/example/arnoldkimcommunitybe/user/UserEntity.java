package com.example.arnoldkimcommunitybe.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String username;
    private String profile;

    @Builder
    public UserEntity(String email, String password, String username, String profile) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.profile = profile;
    }
}
