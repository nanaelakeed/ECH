package com.bfcai.ECH.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "symptoms")
@Data
@NoArgsConstructor
public class Symptoms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "symptom")
    private String symptom;

    @ManyToMany
    @JoinTable(name = "disease_symptoms",
    joinColumns = {@JoinColumn(name = "symptom_id")},
    inverseJoinColumns = {@JoinColumn(name = "disease_id")})
    List<Diseases> diseases;

}
