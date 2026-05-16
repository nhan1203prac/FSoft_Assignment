package com.fa_training.finalTest.Repository;

import com.fa_training.finalTest.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
