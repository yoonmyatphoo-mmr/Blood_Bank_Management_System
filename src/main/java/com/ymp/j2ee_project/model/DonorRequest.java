package com.ymp.j2ee_project.model;

import lombok.Data;

@Data
public class DonorRequest {


    private Long id;

    private Long userId;

    private String  donorName;

    private String phoneNo;

    private String age;

    private String disease;

    private String blood;

    private String date;

    private String address;

    private String gender;
}
