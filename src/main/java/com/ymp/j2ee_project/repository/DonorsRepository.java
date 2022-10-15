package com.ymp.j2ee_project.repository;


import com.ymp.j2ee_project.model.Donors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DonorsRepository extends JpaRepository<Donors,Long> {

    List<Donors> findAll();
}
