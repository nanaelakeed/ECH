package com.bfcai.ECH.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@ToString
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "location")
    private String location;

    @ManyToMany(mappedBy = "doctors")
    private List<Patient> patients;

    public Doctor(String name, String phone, String location) {
        this.name = name;
        this.phone = phone;
        this.location = location;
    }
}
