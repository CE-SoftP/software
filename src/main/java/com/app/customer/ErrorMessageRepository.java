package com.app.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {

    ErrorMessage findByMessage(String message);
}