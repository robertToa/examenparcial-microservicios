package com.util.svcorders.mapper;

import com.util.svcorders.dto.DisResponse;
import com.util.svcorders.dto.OrderRequest;
import com.util.svcorders.dto.OrderResponse;
import com.util.svcorders.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequest orderRequest){
        return Order.builder()
                .dishId(orderRequest.getDishId())
                .customerName(orderRequest.getCustomerName())
                .quantity(orderRequest.getQuantity())
                .build();
    }

    public OrderResponse toResponse(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .dishId(order.getDishId())
                .customerName(order.getCustomerName())
                .quantity(order.getQuantity())
                .total(order.getTotal())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public OrderResponse toResponseWithDish(Order order, DisResponse disResponse){
        OrderResponse loanResponse = toResponse(order);
        if(disResponse != null){
            loanResponse.setDishName(disResponse.getName());
            loanResponse.setDishPrice(disResponse.getPrice());
            loanResponse.setDishAvailable(disResponse.getAvailable());
        }
        return loanResponse;
    }
}
