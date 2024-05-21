package me.yoon.myshop.entity;

import jakarta.persistence.*;
import lombok.*;
import me.yoon.myshop.dto.UserFormDto;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Getter @Setter
@Entity
@ToString
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    @Builder
    public User(String email, String password, UserRoleEnum role){
        this.email=email;
        this.password=password;
        this.role=role;
    }

    @Builder
    public static User createUser(UserFormDto userFormDto, PasswordEncoder passwordEncoder){
        User user = User.builder()
                .email(userFormDto.getEmail())
                .password(passwordEncoder.encode(userFormDto.getPassword()))
                .role(UserRoleEnum.ADMIN)
                .build();
        return user;

    }
}
