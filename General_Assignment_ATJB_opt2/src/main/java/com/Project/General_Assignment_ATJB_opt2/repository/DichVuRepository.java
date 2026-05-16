package com.Project.General_Assignment_ATJB_opt2.repository;

import com.Project.General_Assignment_ATJB_opt2.model.DichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DichVuRepository extends JpaRepository<DichVu, String> {
    @Query("select d from DichVu d where d.maDV like %:kw% or d.tenDV like %:kw%")
    Page<DichVu> findAllByKeyword(@Param("kw") String keyword, Pageable pageable);
}
