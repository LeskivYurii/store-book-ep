package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByEmployeeEmail(String email, Pageable pageable);
    Page<Order> findAllByClientEmail(String email, Pageable pageable);

    @Query("SELECT o FROM Order o where o.id = :id and o.client.email = :email")
    Optional<Order> findByOrderIdAndCustomer(@Param("id") Long id, @Param("email")  String email);
}
