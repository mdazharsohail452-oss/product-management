package com.example.product_management.repo;

import com.example.product_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Productrepo  extends JpaRepository<Product,Long> {
    
}
