package me.yoon.myshop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.LoginRequestDto;
import me.yoon.myshop.dto.UserFormDto;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.entity.UserDetailsImpl;
import me.yoon.myshop.service.UserService;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "userController")
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/logout")
    public String logout(
            @CookieValue(value = "Authorization", defaultValue = "", required = false) Cookie jwtCookie,
                         HttpServletResponse response
    ){
        jwtCookie.setValue("");
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return "redirect:/user/login";
    }
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("userFormDto", new UserFormDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserFormDto userFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "signup";
        }
        try{
            User user = User.createUser(userFormDto,passwordEncoder);
            userService.saveUser(user);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "signup";
        }
        return "redirect:/user/login";
    }
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails!=null){
            log.info(userDetails.getUsername());
            return "redirect:/";
        }
        return "login";
    }
    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "login";
    }

    @PostMapping("/api/login")
    public String login(LoginRequestDto requestDto, HttpServletResponse response){
        log.info("로그인 : "+requestDto.toString());
        userService.login(requestDto,response);
        return "redirect:/";
    }

}
