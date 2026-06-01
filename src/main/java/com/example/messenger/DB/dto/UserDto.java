package com.example.messenger.DB.dto;

import com.example.messenger.DB.User;

import java.time.Instant;
import java.time.LocalDate;

public class UserDto{
    private Long id;
    private String login;
    private String first_name;
    private String last_name;
    private LocalDate birth_date;
    private String avatar_url;
    private Instant last_seen;

    public UserDto(User user){
        this.id = user.getId();
        this.login = user.getLogin();
        this.first_name = user.getFirstName();
        this.last_name = user.getLastName();
        this.birth_date = user.getBirthDate();
        this.avatar_url = user.getAvatarUrl();
        this.last_seen = user.getLastSeen();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Instant getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(Instant last_seen) {
        this.last_seen = last_seen;
    }
}

