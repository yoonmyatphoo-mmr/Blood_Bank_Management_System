package com.ymp.j2ee_project.repository;


import com.ymp.j2ee_project.model.Donors;
import com.ymp.j2ee_project.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientsRepository extends JpaRepository<Patients,Long> {

    List<Patients> findAll();

    List<Patients> findByUserId(Long id);
    @Query(value = "select * from patients s where s.address like %:keyword% or s.id like %:keyword% or s.blood like %:keyword%",
            nativeQuery = true)
    List<Patients> findByKeyword(@Param("keyword") String keyword);
}
