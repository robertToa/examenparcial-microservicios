package com.util.svcmenu.controller;

import com.util.svcmenu.dto.DishRequest;
import com.util.svcmenu.dto.DishResponse;
import com.util.svcmenu.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu/dishes")
@RequiredArgsConstructor
@Slf4j
public class DishController {
    private final DishService dishService;

    @GetMapping
    public ResponseEntity<List<DishResponse>> getAllDishes(){
        log.info("GET /api/menu/dishes - Obteniendo todos los platos");
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getDishById(@PathVariable Long id){
        log.info("GET /api/menu/dishes/{} - Obteniendo plato por id", id);
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @PostMapping
    public ResponseEntity<DishResponse> createDish(@Valid @RequestBody DishRequest dishRequest){
        log.info("POST /api/menu/dishes - Creando un plato con nombre: {}", dishRequest.getName());
        DishResponse createdDish = dishService.createDish(dishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDish);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponse> updateDish(@PathVariable Long id, @Valid @RequestBody DishRequest dishRequest){
        log.info("PUT /api/menu/dishes/{} - Actualizando plato por id", id);
        return ResponseEntity.ok(dishService.updateDish(id, dishRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DishResponse> deleteDish(@PathVariable Long id){
        log.info("DELETE /api/menu/dishes/{} - Plato eliminado", id);
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }
}
