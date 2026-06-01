package com.example.messenger.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class FileUploadController {
    @PostMapping("/api/public/uploadAvatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            throw new RuntimeException("Empty file");
        }
        try{
            Path uploadPath = Path.of("uploads/userAvatars");
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);

            return ResponseEntity.ok("Successful upload");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
