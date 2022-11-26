package com.bfcai.ECH.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "medicine")
@Data
@ToString
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "M_name")
    private String M_name;

    @Column(name = "duration")
    private int duration;

    @Column(name = "frequency")
    private int freuency;

    @Column(name = "time")
    private float time;

    @Column(name = "number_pills")
    private int number_pills;

    @ManyToOne
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    private Patient patient;

    public Medicine(String m_name, int duration, int freuency, float time, int number_pills, Patient patient) {
        M_name = m_name;
        this.duration = duration;
        this.freuency = freuency;
        this.time = time;
        this.number_pills = number_pills;
        this.patient = patient;
    }
}
