package edu.sysu.sdcs.coupon.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String role;
}