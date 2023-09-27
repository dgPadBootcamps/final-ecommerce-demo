package com.dgpad.ecommerceuserdemo.repository;

import com.dgpad.ecommerceuserdemo.model.Product;
import com.dgpad.ecommerceuserdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where name = ?1", nativeQuery = true)
    Optional<Product> findProductByName(String name);

    boolean existsByName(String name);
}