package com.bfcai.ECH.dao;

import com.bfcai.ECH.entity.Companion;
import com.bfcai.ECH.entity.Doctor;
import com.bfcai.ECH.entity.Medicine;
import com.bfcai.ECH.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "select p.companions from Patient p where p.id=:patientId")
    List<Companion> findAllCompanionsForPatient(@Param(value = "patientId") Long patientId);


    @Query(value = "select p from Patient p where p.email=:email or p.phone=:phone")
    List<Patient> findPatientsByEmailOrPhone(@Param(value = "email") String email, @Param("phone") String phone);


    @Query(value = "select p.medicines from Patient p where p.id=:patientId")
    List<Medicine> findAllMedicineForPatient(@Param(value = "patientId") Long patientId);

    @Query(value = "select p.doctors from Patient p where p.id=:patientId")
    List<Doctor> findAllDoctorsForPatient(@Param(value = "patientId") Long patientId);


    @Query(value = "select distinct p from Patient p where p.id in :patientsIds")
    Set<Patient> fetchPatientsByIds(List<Long> patientsIds);
}