package com.example.messenger.Controllers;

import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.UserDto;
import com.example.messenger.DB.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/public/token")
    public void loadToken(){
    }

    @PostMapping("/api/public/registration")
    public void registration(@RequestBody User user){
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
    }

    @GetMapping("/api/profile/get")
    public UserDto getProfileInformation(@AuthenticationPrincipal UserDetails userDetails){
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(ResourceNotFoundException::new);
        user.setAvatarUrl("http://localhost:8080/uploads/userAvatars/" + user.getAvatarUrl());
        return new UserDto(user);
    }
}
