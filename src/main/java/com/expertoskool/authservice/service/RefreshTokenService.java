package com.expertoskool.authservice.service;

import com.expertoskool.authservice.model.RefreshToken;
import com.expertoskool.authservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Transactional
    public void save(UUID userId, String token, Long expiry) {
        repository.deleteByUserId(userId);

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(token)
                .expiry(expiry)
                .build();

        repository.save(refreshToken);
    }

    public Optional<RefreshToken> validateRefreshToken(String token) {
        return repository.findByToken(token).filter(rt -> rt.getExpiry() > System.currentTimeMillis());
    }

}
