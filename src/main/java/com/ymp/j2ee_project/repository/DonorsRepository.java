package com.ymp.j2ee_project.repository;


import com.ymp.j2ee_project.model.Donors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DonorsRepository extends JpaRepository<Donors,Long> {


    List<Donors> findByUserId(Long id);
    List<Donors> findAll();

   /* @Query( nativeQuery = true, value = "select * from donors s where s.address like :addr%")*/
    @Query(value = "select * from donors s where s.address like %:keyword% or s.id like %:keyword% or s.blood like %:keyword%",
            nativeQuery = true)
    List<Donors> findByKeyword(@Param("keyword") String keyword);

    //    List<Donors> findByAddress(String addr);
}
