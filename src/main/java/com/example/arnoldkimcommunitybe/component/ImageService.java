package com.example.arnoldkimcommunitybe.component;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageService {
    private final String uploadDir = "/Users/saaaayho/Desktop/images";

    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new NullPointerException("File is empty");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);

        Files.write(path, file.getBytes());

        return fileName;
    }
}
