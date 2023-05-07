package com.bfcai.ECH.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiseasePredictionResponse {
    private float probability;
    private String disease;
    private String description;
}
