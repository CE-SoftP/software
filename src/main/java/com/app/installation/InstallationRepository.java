package com.app.installation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstallationRepository extends JpaRepository<InstallationDB, Integer> {
    @Query("SELECT i FROM InstallationDB i WHERE i.checkedUser = :checkedUser AND i.customerId = :customerId")
    List<InstallationDB> findByCHECKED_USERAndCustomerId(@Param("checkedUser") String checkedUser, @Param("customerId") int customerId);


    @Query("SELECT i FROM InstallationDB i WHERE i.customerId = :customerId")
    List<InstallationDB> findByCustomerId(@Param("customerId")int customerId);
    @Query("SELECT i FROM InstallationDB i WHERE i.checked = :checked")
    List<InstallationDB> findByCheckedAdmin(@Param("checked")String checked);
}