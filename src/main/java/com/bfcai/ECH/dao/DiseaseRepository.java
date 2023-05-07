package com.bfcai.ECH.dao;


import com.bfcai.ECH.entity.Diseases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Diseases,Long> {

    @Query(value = "select d.description from Diseases d where d.disease=:diseaseName")
    String fetchDescriptionForDisease(@Param(value = "diseaseName") String diseaseName);
}
