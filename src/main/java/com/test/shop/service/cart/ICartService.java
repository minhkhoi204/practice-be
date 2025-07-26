package com.test.shop.service.cart;

import com.test.shop.model.Cart;
import com.test.shop.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
    //Long initializeNewCart();

}