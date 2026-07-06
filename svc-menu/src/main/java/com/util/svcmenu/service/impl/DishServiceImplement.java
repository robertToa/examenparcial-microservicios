package com.util.svcmenu.service.impl;

import com.util.svcmenu.dto.DishRequest;
import com.util.svcmenu.dto.DishResponse;
import com.util.svcmenu.exception.DuplicateResourceException;
import com.util.svcmenu.exception.ResourceNotFoundException;
import com.util.svcmenu.mapper.DishMapper;
import com.util.svcmenu.model.Dish;
import com.util.svcmenu.repository.DishRepository;
import com.util.svcmenu.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImplement implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DishResponse> getAllDishes(){
        log.info("Obteniendo todos los platos");
        return dishRepository.findAll()
                .stream()
                .map(dishMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DishResponse getDishById(Long id){
        log.info("Obteniendo plato con id: {}", id);
        Dish dish =  dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plato no encontrado con el id: "+ id));
        return dishMapper.toResponse(dish);
    }

    @Override
    @Transactional
    public DishResponse createDish(DishRequest dishRequest){
        log.info("Nuevo plato creado con ISBN: {}", dishRequest.getName());
        if(dishRepository.existsByName(dishRequest.getName())){
            throw new DuplicateResourceException("Ya existe un plato con el nombre: " + dishRequest.getName());
        }
        Dish dish = dishMapper.toEntity(dishRequest);
        Dish saveDish = dishRepository.save(dish);
        log.info("Plato creado exitosamente con el id: {}", saveDish.getId());
        return dishMapper.toResponse(saveDish);
    }

    @Override
    @Transactional
    public DishResponse updateDish(Long id, DishRequest dishRequest){
        log.info("Plato actualizado con el id: {}", id);
        Dish existingDish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plato no encontrado con id: " + id));
        if(dishRepository.existsByIsbnAndIdNo(dishRequest.getName(), id)){
            throw new DuplicateResourceException("Nombre usado en otro plato: "+ dishRequest.getName());
        }
        dishMapper.updateEntityFromRequest(dishRequest, existingDish);
        Dish updateDish = dishRepository.save(existingDish);
        log.info("Plato actualziado exitosamente con id: {}", updateDish.getId());
        return dishMapper.toResponse(updateDish);
    }

    @Override
    @Transactional
    public void deleteDish(Long id){
        log.info("Plato eliminado con id: {}", id);
        if(!dishRepository.existsById(id)){
            throw  new ResourceNotFoundException("Plato no encontrado con id: " + id);
        }
        dishRepository.deleteById(id);
        log.info("Plato eliminado exitosamente con id: {}", id);
    }
}
