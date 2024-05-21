package me.yoon.myshop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.config.JwtUtil;
import me.yoon.myshop.dto.LoginRequestDto;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "userService")
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        User checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser != null) throw new IllegalStateException("중복된 회원입니다.");
        return userRepository.save(user);
    }
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        log.info("login"+loginRequestDto.toString());
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if(user==null) throw new EntityNotFoundException("해당 유저가 존재하지 않습니다.");

        try{
            String token = jwtUtil.createToken(user.getEmail(), user.getRole());
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");

            Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,token );
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            cookie.setSecure(false);

            response.addCookie(cookie);
        }catch(UnsupportedEncodingException e){
            log.error(e.getMessage());
        }
    }
}

