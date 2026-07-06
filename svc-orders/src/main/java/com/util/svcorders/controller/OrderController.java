package com.util.svcorders.controller;

import com.util.svcorders.dto.OrderRequest;
import com.util.svcorders.dto.OrderResponse;
import com.util.svcorders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllLOrders() {
        log.info("GET /api/orders - Cargar todas las ordenes");
        return ResponseEntity.ok(orderService.getAllLOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        log.info("GET /api/orders/{} - Cargar la orden por id", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest request) {
        log.info("POST /api/orders - Crear order con el plato con id: {}", request.getDishId());
        OrderResponse createdOrder = orderService.createOrder(request);
        // 201 Created — recurso creado exitosamente
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}
