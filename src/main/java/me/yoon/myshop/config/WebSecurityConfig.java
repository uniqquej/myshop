package me.yoon.myshop.config;

import lombok.RequiredArgsConstructor;
import me.yoon.myshop.entity.UserRoleEnum;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.csrf().disable();

       http
           .authorizeHttpRequests()
           .requestMatchers("/").permitAll()
           .requestMatchers("/user/**","/user/api/login").permitAll()
           .requestMatchers("/item/**").permitAll()
           .requestMatchers("/cart/**").permitAll()
           .requestMatchers("/review/**").permitAll()
           .requestMatchers("/admin/**").hasRole(UserRoleEnum.ADMIN.toString())
           .anyRequest().authenticated();

       http
            .formLogin()
            .loginPage("/user/login")
            .defaultSuccessUrl("/")
            .failureUrl("/user/login/error")
            .and()
            .logout()
            .logoutSuccessUrl("/user/login");

       http
           .exceptionHandling()
           .accessDeniedPage("/forbidden");

    return http.build();
    }

}