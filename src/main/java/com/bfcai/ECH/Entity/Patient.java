package com.bfcai.ECH.Entity;


import jakarta.persistence.*;
import lombok.*;
import com.bfcai.ECH.type.GenderType;

import java.util.Set;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@ToString
public class Patient {
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

    @Column(name = "diseases")
    private String diseases;

    @Column(name = "gender")
    private GenderType gender;

    @OneToMany(mappedBy = "patient")
    private Set<Companion> companions;

    @OneToMany(mappedBy = "patient")
    private Set<Medicine> medicines;

    @ManyToOne
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    private Doctor doctor;

}
