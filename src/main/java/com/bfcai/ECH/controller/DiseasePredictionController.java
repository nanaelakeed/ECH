package com.bfcai.ECH.controller;


import com.bfcai.ECH.dto.DiseasePredictionResponse;
import com.bfcai.ECH.service.DiseasePredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/diseases-prediction")
@RequiredArgsConstructor
public class DiseasePredictionController {

    private final DiseasePredictionService diseasePredictionService;

    @PostMapping
    public List<DiseasePredictionResponse> fetchExpectedDiseases(@RequestBody List<String> symptoms){
        return this.diseasePredictionService.fetchExpectedDiseases(symptoms);
    }
}
