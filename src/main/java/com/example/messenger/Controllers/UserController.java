package com.example.messenger.Controllers;

import com.example.messenger.DB.User;
import com.example.messenger.DB.dto.UserDto;
import com.example.messenger.DB.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/public/registration")
    public void registration(@RequestBody User user){
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
    }

    /*
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/api/findAll")
    public ArrayList<UserDto> findAll(){
        ArrayList<UserDto> list = new ArrayList<>();
        Iterable<User> userIter = userRepository.findAll();

        for (User user: userIter){
            list.add(new UserDto(user));
        }
        return list;
    } */
}
