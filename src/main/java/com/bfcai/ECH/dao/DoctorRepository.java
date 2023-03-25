package com.bfcai.ECH.dao;

import com.bfcai.ECH.entity.Doctor;
import com.bfcai.ECH.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "select d from Doctor d where d.phone=:phone")
    List<Doctor> findDoctorByPhone(@Param(value = "phone")String phone);

    @Query(value = "select d.patients from Doctor d where d.id=:id")
    List<Patient> getAllDoctorPatients(@Param(value = "id")Long id);
}