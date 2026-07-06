package com.util.svcorders.repository;


import com.util.svcorders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {
    @Query("SELECT COUNT(o) > 0 FROM Order o WHERE o.dishId = :dishId " +
            "AND o.customerName = :customerName AND o.status = 'PENDING'")
    boolean existsActiveOrderForCustomerNameAndDish(@Param("dishId") Long dishId,
                                           @Param("customerName") String customerName);
}
