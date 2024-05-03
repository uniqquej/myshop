package me.yoon.myshop.controller;

import lombok.RequiredArgsConstructor;
import me.yoon.myshop.dto.AccessTokenRequestDto;
import me.yoon.myshop.dto.AccessTokenResponseDto;
import me.yoon.myshop.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<AccessTokenResponseDto> createNewAccessToken(
            @RequestBody AccessTokenRequestDto requestDto
    ){
        String newAccessToken = tokenService.createNewAccessToken(requestDto.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AccessTokenResponseDto(newAccessToken));
    }

}
