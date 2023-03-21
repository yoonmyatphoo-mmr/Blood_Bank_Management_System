package com.ymp.j2ee_project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;


}
