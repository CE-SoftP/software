package com.app.maneger_and_product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface CatagroisRepositary extends JpaRepository<Catagroies, Integer> {
    @Query("SELECT DISTINCT c.category FROM Catagroies c")
    List<String> findDistinctCategories();
    boolean existsByName(String name);
    Catagroies findByName(String categoryName);


    void deleteById(int id);
}

