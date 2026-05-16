package com.Project.General_Assignment_ATJB_opt2.repository;

import com.Project.General_Assignment_ATJB_opt2.model.May;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MayRepository extends JpaRepository<May, String> {
    @Query("select m from May m where m.maMay like %:kw% or m.viTri like %:kw%")
    Page<May> findAllByKeyword(@Param("kw") String keyword, Pageable pageable);
}
