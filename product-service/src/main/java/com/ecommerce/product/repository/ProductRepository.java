package com.ecommerce.product.repository;

/**
 * Add this method to your existing ProductRepository interface.
 *
 * Spring Data JPA will auto-implement it — no @Query needed.
 * It fetches all products whose IDs are in the given list, sorted by ID,
 * which is required for the purchase logic to match requests to products correctly.
 */

import com.ecommerce.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // ADD this method to your existing repository:
    List<Product> findAllByIdInOrderById(List<Integer> ids);
}
