package com.bfcai.ECH.service;


import com.bfcai.ECH.dao.DoctorRepository;
import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.entity.Doctor;
import com.bfcai.ECH.entity.Patient;
import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import com.bfcai.ECH.wrapper.DoctorWrapper;
import com.bfcai.ECH.wrapper.PatientWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    @PersistenceContext
    private EntityManager entityManager;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public ApiResponseDto saveDoctor(Doctor doctor){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        if(this.validateDoctorByPhone(doctor)){
            responseDto=ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(this.modelMapper.map(this.doctorRepository.save(doctor), DoctorWrapper.class))
                            .count(1L)
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        }else{
            responseDto.setCode(StatusCode.ALREADY_EXISTS.serverCode);
            responseDto.setMessage(StatusMessage.ALREADY_EXISTS);
        }
        return responseDto;
    }

    @Transactional
    public void savePatientDoctor(Doctor doctor) {
        for (Patient patient: doctor.getPatients()) {
            this.entityManager.createNativeQuery("insert into patient_doctors (patient_id,doctor_id) values (?,?)")
                    .setParameter(1, patient.getId())
                    .setParameter(2, doctor.getId())
                    .executeUpdate();
        }
    }

    public boolean validateDoctorByPhone(Doctor doctor){
        List<Doctor> doctors=this.doctorRepository.findDoctorByPhone(doctor.getPhone());
        return doctors.isEmpty();
    }

    @Transactional
    public ApiResponseDto deleteDoctor(Long id){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        if(this.doctorRepository.findById(id).isPresent()) {
            this.entityManager.createNativeQuery("delete from patient_doctors where doctor_id=?")
                    .setParameter(1,id)
                    .executeUpdate();
            this.doctorRepository.deleteById(id);
             responseDto = ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(null)
                            .count(1L)
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        }else{
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }

    public ApiResponseDto getAllDoctorPatients(Long id){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        if(this.doctorRepository.findById(id).isPresent()) {
            List<PatientWrapper> patients=this.doctorRepository.getAllDoctorPatients(id)
                    .stream()
                    .map(patient -> this.modelMapper.map(patient,PatientWrapper.class))
                    .collect(Collectors.toList());
            responseDto = ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(patients)
                            .count((long) this.doctorRepository.getAllDoctorPatients(id).size())
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        }else{
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }
}
