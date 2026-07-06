package com.util.svcorders.service.impl;

import com.util.svcorders.client.MenuWebClient;
import com.util.svcorders.dto.DisResponse;
import com.util.svcorders.dto.OrderRequest;
import com.util.svcorders.dto.OrderResponse;
import com.util.svcorders.exception.BusinessRuleException;
import com.util.svcorders.exception.ResourceNotFoundException;
import com.util.svcorders.mapper.OrderMapper;
import com.util.svcorders.model.Order;
import com.util.svcorders.repository.OrderRepository;
import com.util.svcorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImplement implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final MenuWebClient menuWebClient;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request){
        log.info("Creando una orden con el plato id: {} - user: {}", request.getDishId(), request.getCustomerName());
        if(orderRepository.existsActiveOrderForCustomerNameAndDish(request.getDishId(), request.getCustomerName())){
            throw new BusinessRuleException("Nombre de la mesa " + request.getCustomerName() +
                    "Ya existe una orden con estado PENDIENTE del plato con id: " +
                    request.getDishId());
        }
        log.info("Verificando disponibilidad del plato via WebClient...");
        DisResponse dishResponse = menuWebClient.getDishById(request.getDishId());
        if(!dishResponse.getAvailable()){
            throw new ResourceNotFoundException(
                    "Plato con id: "+ request.getDishId() +
                            "No esta disponible "
            );
        }
        Order order = orderMapper.toEntity(request);
        order.setTotal(order.getQuantity() * dishResponse.getPrice());
        Order savedOrder = orderRepository.save(order);
        log.info("Orden creado exitosamente con id: {}", savedOrder.getId());
        return orderMapper.toResponseWithDish(savedOrder, dishResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllLOrders(){
        log.info("Obteniendo todas las ordenes");
        return orderRepository.findAll()
                .stream()
                .map( order -> {
                    try{
                        DisResponse dish = menuWebClient.getDishById(order.getDishId());
                        return orderMapper.toResponseWithDish(order, dish);
                    }catch (Exception ex){
                        log.warn("No se logro obtener el detalle del plato de la orden con id: {}", order.getId());
                        return orderMapper.toResponse(order);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id){
        log.info("Obteniendo orden con id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plato no encontrado con id: "+id));
        DisResponse dishDeatil = menuWebClient.getDishById(order.getDishId());
        return orderMapper.toResponseWithDish(order, dishDeatil);
    }
}
