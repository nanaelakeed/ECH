package com.bfcai.ECH.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "diseases")
@Data
@NoArgsConstructor
public class Diseases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "disease")
    private String disease;

    @Column(name = "description",length = 2550)
    private String description;

    @ManyToMany(mappedBy = "diseases")
    List<Symptoms> symptoms;
}
