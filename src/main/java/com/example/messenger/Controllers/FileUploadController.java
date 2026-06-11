package com.example.messenger.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/api/public/uploadAvatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            throw new RuntimeException("Empty file");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File too large");
        }
        try{
            Path uploadPath = Path.of("uploads/userAvatars");
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            String originalFileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID() + "." + originalFileExtension;
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            return newFileName;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
