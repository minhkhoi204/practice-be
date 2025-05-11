package com.test.shop.service.cart;

import com.test.shop.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    //get accessory from controller
    CartItem getCartItem(Long cartId, Long productId);
}
