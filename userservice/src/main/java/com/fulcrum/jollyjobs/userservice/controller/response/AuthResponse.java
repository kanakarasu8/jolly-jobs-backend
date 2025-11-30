package com.fulcrum.jollyjobs.userservice.controller.response;
import com.fulcrum.jollyjobs.userservice.data.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private User user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
