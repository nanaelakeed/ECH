package com.bfcai.ECH.entity;


import jakarta.persistence.*;
import lombok.*;
import com.bfcai.ECH.type.GenderType;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "diseases")
    private String diseases;

    @Column(name = "gender")
    private GenderType gender;


    @Column(name = "phone", unique = true)
    private String phone;

    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }, targetEntity = Companion.class,fetch = FetchType.LAZY)
    @JoinTable(name = "patient_companion",
            joinColumns = {@JoinColumn(name = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "companion_id")}
    )
    Set<Companion> companions;

    @OneToMany(mappedBy = "patient", orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Medicine> medicines;

    @ManyToMany
    @JoinTable(name = "patient_doctors",
            joinColumns = {@JoinColumn(name = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id"),}
    )
    List<Doctor> doctors;

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", diseases='" + diseases + '\'' +
                ", gender=" + gender +
                '}';
    }
}
