package com.bfcai.ECH.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue")
@Data
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "symptom_name")
    private String symptom_name;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "treatment")
    private String treatment;

    public Issue(String symptom_name, String description, String name, String treatment) {
        this.symptom_name = symptom_name;
        this.description = description;
        this.name = name;
        this.treatment = treatment;
    }
}
