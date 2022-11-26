package com.bfcai.ECH.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public Doctor(String name, String phone, String location) {
        this.name = name;
        this.phone = phone;
        this.location = location;
    }
}
