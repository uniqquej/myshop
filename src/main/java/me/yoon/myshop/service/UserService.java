package me.yoon.myshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.SignupRequestDto;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.entity.UserRoleEnum;
import me.yoon.myshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public Long save(SignupRequestDto requestDto){
        UserRoleEnum role = UserRoleEnum.USER;

        return userRepository.save(User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(role)
                .build()).getId();
    }
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
}

