package com.bfcai.ECH.dao;

import com.bfcai.ECH.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {


    @Query(value = "select m from Medicine m where m.patient.id=:patientId")
    List<Medicine> findAllByPatientId(@Param(value = "patientId")Long patientId);



}