package com.util.svcmenu.service;

import com.util.svcmenu.dto.DishRequest;
import com.util.svcmenu.dto.DishResponse;

import java.util.List;

public interface DishService {
    List<DishResponse> getAllDishes();

    DishResponse getDishById(Long id);

    DishResponse createDish(DishRequest dishRequest);

    DishResponse updateDish(Long id, DishRequest dishRequest);

    void deleteDish(Long id);
}
