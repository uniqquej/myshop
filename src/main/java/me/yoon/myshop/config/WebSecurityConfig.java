package me.yoon.myshop.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
            .formLogin()
            .loginPage("/user/login")
            .usernameParameter("email")
            .defaultSuccessUrl("/")
            .and()
            .logout()
            .logoutSuccessUrl("/user/login")
            .and()
            .csrf().disable();

       http
           .authorizeHttpRequests()
           .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
           .requestMatchers("/","/user/**","/item/**","/cart/**").permitAll()
           .requestMatchers("/admin/**").hasRole("ADMIN")
           .anyRequest().authenticated();

       http
           .exceptionHandling()
           .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}