package me.yoon.myshop.controller;

import me.yoon.myshop.dto.UserFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login-page")
    public String login(){
        return "login";
    }
    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "/member/memberLoginForm";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("userFormDto", new UserFormDto());
        return "signup";
    }
}
