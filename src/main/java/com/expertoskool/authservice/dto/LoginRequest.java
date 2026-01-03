package com.expertoskool.authservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginRequest {

    private UUID userId;

    private long phoneNumber;

    private String email;

    private String password;
}