package com.util.svcorders.service;

import com.util.svcorders.dto.OrderRequest;
import com.util.svcorders.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllLOrders();

    OrderResponse getOrderById(Long id);
}
