package com.ymp.j2ee_project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "donors")

public class Donors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String  donorName;

    private String phoneNo;

    private String age;

    private String disease;

    private String address;

    private String blood;

    private Date date;

    private String gender;
}
