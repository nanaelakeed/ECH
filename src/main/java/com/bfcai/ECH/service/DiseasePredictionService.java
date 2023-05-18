package com.bfcai.ECH.service;

import com.bfcai.ECH.dao.DiseaseRepository;
import com.bfcai.ECH.dto.DiseasePredictionResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@RequiredArgsConstructor
public class DiseasePredictionService {

    @PersistenceContext
    private EntityManager entityManager;

    private final DiseaseRepository diseaseRepository;

    @SuppressWarnings("unchecked")
    public List<DiseasePredictionResponse> fetchExpectedDiseases(List<String> symptoms) {

        List<String> diseases = (List<String>) this.entityManager.createNativeQuery("select d.disease from diseases d join" +
                        " (select disease_id from disease_symptoms where symptom_id in" +
                        " (select s.id from symptoms s where s.symptom in (?))) dj" +
                        " on d.id=dj.disease_id")
                .setParameter(1, symptoms)
                .getResultList();


        Set<String> notDuplicatedDiseases = new HashSet<>(diseases);

        List<DiseasePredictionResponse> diseasePredictionResponses = new ArrayList<>();

        notDuplicatedDiseases.forEach(value -> diseasePredictionResponses.add(
                new DiseasePredictionResponse(
                        ((float) diseases.stream().filter(disease -> disease.equals(value)).count() / (float) diseases.size() * (float) 100.0),
                        value,
                        this.diseaseRepository.fetchDescriptionForDisease(value)

                )
        ));

        diseasePredictionResponses.sort((o1, o2) -> (int) (o2.getProbability() - o1.getProbability()));
        List<DiseasePredictionResponse> result=new ArrayList<>();
        float sum=diseasePredictionResponses.get(0).getProbability()+diseasePredictionResponses.get(1).getProbability();
        diseasePredictionResponses.get(0).setProbability(((diseasePredictionResponses.get(0).getProbability())/sum)*100);
        diseasePredictionResponses.get(1).setProbability(((diseasePredictionResponses.get(1).getProbability())/sum)*100);
        result.add(diseasePredictionResponses.get(0));
        result.add(diseasePredictionResponses.get(1));
        return result;
    }
}
