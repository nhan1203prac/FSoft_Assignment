package com.fa_training.finalTest.Repository;

import com.fa_training.finalTest.Model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, String> {
}
