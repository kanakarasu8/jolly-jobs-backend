package com.fulcrum.jollyjobs.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class AdminRequest {
    @NotNull
    @Email
    private String adminEmail;
}
