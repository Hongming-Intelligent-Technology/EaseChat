package com.easechat.easechat.controller;

import com.easechat.easechat.common.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileController {
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return R.fail("File is empty");
        }

        String originalName = file.getOriginalFilename();
        String safeName = originalName == null ? "file" : Paths.get(originalName).getFileName().toString();
        String extension = "";
        int dotIndex = safeName.lastIndexOf('.');
        if (dotIndex > -1) {
            extension = safeName.substring(dotIndex);
        }
        String fileName = UUID.randomUUID() + extension;

        Path uploadDir = Paths.get("uploads");
        Path target = uploadDir.resolve(fileName);

        try {
            Files.createDirectories(uploadDir);
            Files.copy(file.getInputStream(), target);
            return R.data("/upload/" + fileName);
        } catch (IOException e) {
            return R.fail("File upload failed");
        }
    }
}
