package me.yoon.myshop.repository;

import me.yoon.myshop.entity.Cart;
import me.yoon.myshop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
