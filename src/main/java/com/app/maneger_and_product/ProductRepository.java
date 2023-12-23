package com.app.maneger_and_product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository <ProductDb, Integer>{
    @Query("SELECT p FROM ProductDb p WHERE p.category.id = :categoryId")
    List<ProductDb> findByCategoryId(@Param("categoryId") int categoryId);
    Optional<ProductDb> findByProductNameContainingIgnoreCase(String term);


}
