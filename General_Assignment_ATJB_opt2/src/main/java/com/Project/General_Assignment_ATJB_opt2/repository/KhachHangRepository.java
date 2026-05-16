package com.Project.General_Assignment_ATJB_opt2.repository;

import com.Project.General_Assignment_ATJB_opt2.model.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {

    @Query("select k from KhachHang k where k.maKH like %:kw% or k.tenKH like %:kw%")
    Page<KhachHang> findAllByKeyword(@Param("kw") String keyword, Pageable pageable);
}
