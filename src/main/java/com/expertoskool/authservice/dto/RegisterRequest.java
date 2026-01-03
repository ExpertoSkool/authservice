package com.expertoskool.authservice.dto;
import lombok.Data;
import java.util.UUID;

@Data
public class RegisterRequest {

    private UUID userId;

    private String userFirstName;

    private String userMiddleName;

    private String userLastName;

    private long phoneNumber;

    private String email;

    private String password;
}
