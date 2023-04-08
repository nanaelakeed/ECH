package com.bfcai.ECH.wrapper;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MedicineWrapper {
    private String M_name;

    private String duration;

    private int frequency;

    private float time;

    private int number_pills;
}
