package com.bfcai.ECH.service;


import com.bfcai.ECH.dao.PatientRepository;
import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.entity.Companion;
import com.bfcai.ECH.entity.Medicine;
import com.bfcai.ECH.entity.Patient;
import com.bfcai.ECH.exception.ConflictException;
import com.bfcai.ECH.exception.NotFoundException;
import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import com.bfcai.ECH.wrapper.CompanionWrapper;
import com.bfcai.ECH.wrapper.DoctorWrapper;
import com.bfcai.ECH.wrapper.MedicineWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final MedicineService medicineService;

    public ApiResponseDto<Patient> FindById(Long id) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        Patient fetchedPatient = this.patientRepository.findById(id).orElse(null);
        if (fetchedPatient != null) {
            responseDto = ApiResponseDto
                    .builder()
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .responseData(
                            ResponseData.builder()
                                    .data(fetchedPatient)
                                    .count(1L)
                                    .build()
                    )
                    .build();

        } else {
            throw new NotFoundException("No patient exist with id " + id);
        }
        return responseDto;
    }


    public ApiResponseDto<List<Companion>> findCompanionsForPatient(Long id) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        if(!this.validatePatientData(this.patientRepository.findById(id).orElse(null))) {
            List<CompanionWrapper> companions = this.patientRepository.findAllCompanionsForPatient(id)
                    .stream()
                    .map(companion -> this.modelMapper.map(companion, CompanionWrapper.class))
                    .collect(Collectors.toList());
            if (companions != null) {
                responseDto = ApiResponseDto
                        .builder()
                        .code(StatusCode.SUCCESS.serverCode)
                        .message(StatusMessage.SUCCESS)
                        .responseData(
                                ResponseData.builder()
                                        .data(companions)
                                        .count((long) companions.size())
                                        .build()
                        )
                        .build();
            } else {
                responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
                responseDto.setMessage(StatusMessage.NOT_FOUND);
            }
        }
        return responseDto;
    }



    public ApiResponseDto save(Patient patient) {
        if (this.validatePatientData(patient)) {
            throw new ConflictException("Email or phone already existed");
        } else {
            return ApiResponseDto.builder()
                    .responseData(
                            ResponseData.builder()
                                    .data(this.patientRepository.save(patient))
                                    .count(1L)
                                    .build()
                    )
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.CREATED)
                    .build();
        }
    }

    private boolean validatePatientData(Patient patient) {
        List<Patient> patients = this.patientRepository.findPatientsByEmailOrPhone(patient.getEmail(), patient.getPhone());
        return patients.isEmpty();
    }

    public ApiResponseDto getAllMedicineForPatient(Long patientId) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        List<MedicineWrapper> medicines = this.patientRepository.findAllMedicineForPatient(patientId)
                .stream()
                .map(medicine -> this.modelMapper.map(medicine, MedicineWrapper.class))
                .collect(Collectors.toList());
        if (medicines != null) {
            responseDto = ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(medicines)
                            .count((long) medicines.size())
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        } else {
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }

    public ApiResponseDto getAllDoctorsForPatient(Long patientId) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        List<DoctorWrapper> doctors = this.patientRepository.findAllDoctorsForPatient(patientId)
                .stream()
                .map(doctor -> this.modelMapper.map(doctor, DoctorWrapper.class))
                .collect(Collectors.toList());
        if (doctors != null) {
            responseDto = ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(doctors)
                            .count((long) doctors.size())
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        } else {
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }


    public ApiResponseDto deletePatient(Long patientId) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        Patient patient=this.patientRepository.findById(patientId).orElse(null);
        if(patient!=null){
            this.medicineService.deleteByPatientId(patientId);
            this.patientRepository.deleteById(patientId);
        }
        else {
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
}

    public Set<Patient> fetchPatientsByIds(List<Long> patientsIds) {
        return this.patientRepository.fetchPatientsByIds(patientsIds);
    }
}
