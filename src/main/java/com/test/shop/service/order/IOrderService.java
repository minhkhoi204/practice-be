package com.test.shop.service.order;

import com.test.shop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
