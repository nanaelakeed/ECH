package com.bfcai.ECH.dao;

import com.bfcai.ECH.entity.Companion;
import com.bfcai.ECH.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompanionRepository extends JpaRepository<Companion, Long> {

    @Query(value = "select c from Companion c where c.email=:email and c.phone=:phone")
    List<Companion> findCompanionByEmailOrPhone(@Param(value = "email")String email,@Param(value = "phone")String phone);

    @Query(value = "select c.patients from Companion c where c.id=:companionId")
    List<Patient> findAllPatientsForCompanion(@Param(value = "companionId")Long companionId);

    Optional<Companion> findCompanionByEmail(String email);

    @Query(value = "select c from Companion c left join fetch c.patients where c.id=:companionId")
    Companion getCompanionById(@Param("companionId") Long companionId);


}