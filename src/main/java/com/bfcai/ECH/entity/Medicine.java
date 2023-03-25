package com.bfcai.ECH.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicine")
@Data
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "M_name")
    private String M_name;

    @Column(name = "duration")
    private String duration;

    @Column(name = "frequency")
    private int frequency;

    @Column(name = "time")
    private float time;

    @Column(name = "number_pills")
    private int number_pills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    private Patient patient;

    public Medicine(String m_name, String duration, int frequency, float time, int number_pills, Patient patient) {
        M_name = m_name;
        this.duration = duration;
        this.frequency = frequency;
        this.time = time;
        this.number_pills = number_pills;
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", M_name='" + M_name + '\'' +
                ", duration='" + duration + '\'' +
                ", frequency=" + frequency +
                ", time=" + time +
                ", number_pills=" + number_pills +
                '}';
    }
}
