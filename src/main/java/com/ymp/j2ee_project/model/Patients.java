package com.ymp.j2ee_project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Patients")

public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String patientName;

    private  String phoneNo;

    private String age;

    private Date date;

    private String address;

    private String blood;

    private String gender;
}
