package com.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AppointmenRepository extends JpaRepository<AppointmentDb, Integer> {

}
