package com.test.shop.service.order;

import com.test.shop.dto.OrderDto;
import com.test.shop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
