package me.yoon.myshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupRequestDto {
    private String email;

    private String password;

    private boolean admin=false;
    private String adminToken = "";
}
