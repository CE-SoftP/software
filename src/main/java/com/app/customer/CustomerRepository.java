package com.app.customer;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CustomerRepository extends JpaRepository<CustomerDb,Integer> {

    boolean existsByName(String name);
    boolean existsByEmail(String email);
    CustomerDb findByName(String name);
    Optional <CustomerDb> findByNameAndPass(String name, String pass);

    Optional<CustomerDb> findByEmail(String email);
}
