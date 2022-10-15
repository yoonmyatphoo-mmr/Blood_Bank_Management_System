package com.ymp.j2ee_project.repository;


import com.ymp.j2ee_project.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients,Long> {
}
