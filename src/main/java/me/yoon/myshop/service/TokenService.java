package me.yoon.myshop.service;

import lombok.RequiredArgsConstructor;
import me.yoon.myshop.config.jwt.TokenProvider;
import me.yoon.myshop.entity.RefreshToken;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("유효하지 않은 토큰"));
    }

    public String createNewAccessToken(String refreshToken){
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        Long userId = findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
