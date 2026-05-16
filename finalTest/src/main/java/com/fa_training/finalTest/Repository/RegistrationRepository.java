package com.fa_training.finalTest.Repository;

import com.fa_training.finalTest.Model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, String> {

    @Query("""
            SELECT r FROM Registration r
            WHERE (:name IS NULL OR :name = '' OR LOWER(r.fullName) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:countryID IS NULL OR :countryID = '' OR r.country.countryID = :countryID)
            ORDER BY r.registrationID ASC
            """)
    List<Registration> searchByNameAndCountry(
            @Param("name") String name,
            @Param("countryID") String countryID);


    @Query("SELECT COUNT(r) FROM Registration r")
    long countAll();
}
