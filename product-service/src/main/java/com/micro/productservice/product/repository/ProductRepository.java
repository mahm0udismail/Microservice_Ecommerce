package com.micro.productservice.product.repository;

import com.micro.productservice.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Intger> {

    List<Product> findAllByIdInOrderById(List<Integer> productsIds);

    Product findById(String id);

    Product save(Product product);

    void deleteById(String id);

    boolean existsById(String id);
}