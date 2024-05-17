package me.yoon.myshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Cart(User user){
        this.user = user;
    }

    public static Cart createCart(User user){
        Cart cart = Cart.builder()
                .user(user)
                .build();
        return cart;
    }

}
