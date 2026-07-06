package com.util.svcmenu.mapper;

import com.util.svcmenu.dto.DishRequest;
import com.util.svcmenu.dto.DishResponse;
import com.util.svcmenu.model.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {
    public Dish toEntity(DishRequest dishRequest){
        return Dish.builder()
                .name(dishRequest.getName())
                .description(dishRequest.getDescription())
                .category(dishRequest.getCategory())
                .price(dishRequest.getPrice())
                .available(dishRequest.getAvailable())
                .build();
    }

    public DishResponse toResponse(Dish dish){
        return DishResponse.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .category(dish.getCategory())
                .price(dish.getPrice())
                .available(dish.getAvailable())
                .build();
    }

    public void updateEntityFromRequest(DishRequest dishRequest, Dish dish){
        dish.setName(dishRequest.getName());
        dish.setCategory(dishRequest.getCategory());
        dish.setDescription(dishRequest.getDescription());
        dish.setPrice(dishRequest.getPrice());
        dish.setAvailable(dishRequest.getAvailable());
    }
}
