package com.ymp.j2ee_project.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data

public class PatientsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String patientName;

    private  String phoneNo;

    private String age;

    private String date;

    private String address;

    private String blood;

    private String gender;
}
