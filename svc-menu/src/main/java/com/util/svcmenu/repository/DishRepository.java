package com.util.svcmenu.repository;

import com.util.svcmenu.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DishRepository extends JpaRepository<Dish, Long> {
    boolean existsByName(String name);

    @Query("SELECT COUNT(b)>0 FROM Dish b WHERE b.name = :name AND b.id <> :id")
    boolean existsByIsbnAndIdNo(@Param("name") String name, @Param("id") Long id);
}
