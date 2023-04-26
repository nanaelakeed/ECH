package com.bfcai.ECH.entity;


import com.bfcai.ECH.type.GenderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "companion")
@Data
@NoArgsConstructor
public class Companion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "relative")
    private String relative;

    @JsonIgnore
    @ManyToMany(mappedBy = "companions")
    private List<Patient> patients;


    public Companion(String name, int age, String email, String password, GenderType gender, String phone, String relative) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.relative = relative;
    }

    @Override
    public String toString() {
        return "Companion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", relative='" + relative + '\'' +
                '}';
    }
}
