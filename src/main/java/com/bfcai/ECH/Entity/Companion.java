package com.bfcai.ECH.Entity;


import com.bfcai.ECH.type.GenderType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companion")
@Data
@ToString
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

    @ManyToOne
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    private Patient patient;


    public Companion(String name, int age, String email, String password, GenderType gender, String phone, String relative, Patient patient) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.relative = relative;
        this.patient = patient;
    }
}
