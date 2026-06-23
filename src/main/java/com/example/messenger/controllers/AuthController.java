package com.example.messenger.controllers;

import com.example.messenger.DB.dto.SingleUserLoginDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Проверка, авторизован ли пользователь
@RestController
public class AuthController {
    @GetMapping("/api/public/auth/check")
    public SingleUserLoginDto checkAuth(Authentication auth){

        if (auth != null && auth.isAuthenticated()){
            UserDetails user = (UserDetails) auth.getPrincipal();
            if (user != null){
                return new SingleUserLoginDto(user.getUsername());
            }
        }
        return null;
    }
}
