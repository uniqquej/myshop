package me.yoon.myshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenRequestDto {
    private String refreshToken;
}
