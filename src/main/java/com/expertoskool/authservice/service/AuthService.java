package com.expertoskool.authservice.service;

import com.expertoskool.authservice.dto.AuthResponse;
import com.expertoskool.authservice.dto.LoginRequest;
import com.expertoskool.authservice.dto.RegisterRequest;
import com.expertoskool.authservice.exceptions.InvalidCredentialsException;
import com.expertoskool.authservice.exceptions.UserAlreadyExistsException;
import com.expertoskool.authservice.model.User;
import com.expertoskool.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    public String register(RegisterRequest registerRequest) {

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw  new UserAlreadyExistsException("User already exists.");
        }

        User user =  User.builder().userFirstName(registerRequest.getUserFirstName())
                .userMiddleName(registerRequest.getUserMiddleName())
                .userLastName(registerRequest.getUserLastName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);

        return "User register successfully";
    }

    public AuthResponse login(LoginRequest request) throws Throwable {
        User user = (User) userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid email"));

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String jwtToken = jwtService.generateAccessToken(user);

        String jwtRefreshToken = jwtService.generateRefreshToken(user.getUserId());

        long expiry = System.currentTimeMillis() + 5 * 60 * 1000;

        //Save refresh token in db -> token rotation

        refreshTokenService.save(user.getUserId(), jwtRefreshToken, expiry);

        return new AuthResponse(jwtToken,jwtRefreshToken);

    }
}
