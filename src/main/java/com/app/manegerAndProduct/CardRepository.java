package com.app.manegerAndProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<CardDb, Integer> {
    List<CardDb> findByCustomerDbId(int userId);
}
