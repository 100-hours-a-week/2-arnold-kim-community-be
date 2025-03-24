package com.example.arnoldkimcommunitybe.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        String contentType = file.getContentType();
        if (!"image/png".equals(contentType) && !"image/jpeg".equals(contentType)) {
            return false;
        }

        String filename = file.getOriginalFilename();
        if (filename != null) {
            String ext = StringUtils.getFilenameExtension(filename).toLowerCase();
            return List.of("png", "jpg", "jpeg").contains(ext);
        }
        return true;
    }
}
