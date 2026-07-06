package com.util.svcorders.dto;
import com.util.svcorders.model.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private Long dishId;
    private String customerName;
    private Integer quantity;
    private Double total;
    private OrderStatus status;
    private LocalDateTime createdAt;

    private String dishName;
    private Double dishPrice;
    private Boolean dishAvailable;

}
