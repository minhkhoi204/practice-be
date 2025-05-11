package com.test.shop.repository;

import com.test.shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    //Cart findByUserId(Long userId);
}