package com.ymp.j2ee_project.model;

import lombok.Data;

import javax.persistence.*;

    @Data
    @Entity
    @Table(name = "users")
    public class Users {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 20)
        private String fullName;


        @Column(nullable = false, unique = true, length = 64)
        private String email;

        @Column(nullable = false, length = 64)
        private String password;

        @Column(nullable = false, length = 64)
        private String address;

        private Long roleId;


}
