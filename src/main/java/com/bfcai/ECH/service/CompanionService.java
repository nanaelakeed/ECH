package com.bfcai.ECH.service;

import com.bfcai.ECH.dao.CompanionRepository;
import com.bfcai.ECH.dao.PatientRepository;
import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.LoginRequestDTO;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.entity.Companion;
import com.bfcai.ECH.entity.Patient;
import com.bfcai.ECH.exception.NotFoundException;
import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import com.bfcai.ECH.util.LogUtil;
import com.bfcai.ECH.wrapper.CompanionWrapper;
import com.bfcai.ECH.wrapper.PatientWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanionService {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final CompanionRepository companionRepository;
    private final ModelMapper modelMapper;

    public ApiResponseDto saveCompanion(Companion companion) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        if (this.validateCompanionData(companion)) {
            companion.setPassword(passwordEncoder.encode(companion.getPassword()));
            responseDto = ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(this.companionRepository.save(companion))
                            .count(1L)
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.CREATED)
                    .build();
        } else {
            responseDto.setCode(StatusCode.ALREADY_EXISTS.serverCode);
            responseDto.setMessage(StatusMessage.ALREADY_EXISTS);
        }
        return responseDto;
    }
    public ApiResponseDto authLogin(LoginRequestDTO loginRequestDTO) {
        Companion companion=this.companionRepository.findCompanionByEmail(loginRequestDTO.getEmail()).orElseThrow(()->new NotFoundException("No user with this email found"));
        return ApiResponseDto
                .builder()
                .responseData(
                        ResponseData
                                .builder()
                                .data(passwordEncoder.matches(loginRequestDTO.getPassword(),companion.getPassword()))
                                .build()
                )
                .build();
    }

    @Transactional
    public void savePatientCompanion(Companion companion) {
        for (Patient patient: companion.getPatients()) {
            this.entityManager.createNativeQuery("insert into patient_companion (patient_id,companion_id) values (?,?)")
                    .setParameter(1, patient.getId())
                    .setParameter(2, companion.getId())
                    .executeUpdate();
        }
    }


    private boolean validateCompanionData(Companion companion) {
        List<Companion> companions = this.companionRepository.findCompanionByEmailOrPhone(companion.getEmail(), companion.getPhone());
        return companions.isEmpty();
    }

    public ApiResponseDto getSpecificCompanion(Long companionId) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        CompanionWrapper companion = modelMapper.map(this.companionRepository.findById(companionId), CompanionWrapper.class);
        if (companion != null) {
            responseDto = ApiResponseDto.builder()
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .responseData(ResponseData.builder()
                            .data(companion)
                            .count(1L)
                            .build())
                    .build();
        } else {
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;

    }

    //Getting all patients for companion
    public ApiResponseDto getAllPatientsForCompanion(Long companionId) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        boolean isExist = this.companionExist(companionId);
        if (isExist) {
            List<PatientWrapper> patients = this.companionRepository.findAllPatientsForCompanion(companionId)
                    .stream().map(patient -> this.modelMapper.map(patient, PatientWrapper.class))
                    .collect(Collectors.toList());
            responseDto = ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(patients)
                            .count((long) patients.size())
                            .build())
                    .message(StatusMessage.SUCCESS)
                    .code(StatusCode.SUCCESS.serverCode)
                    .build();
        } else {
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }

    //Check companion exist
    public boolean companionExist(Long companionId) {
        Companion companion = this.companionRepository.findById(companionId).orElse(null);
        if (companion != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public ApiResponseDto updateCompanion(CompanionWrapper companionData,Long companionId){
            Companion savedCompanion=this.companionRepository.findById(companionId).orElseThrow(()->new NotFoundException("No companion found with id : "+companionId));
            savedCompanion.setPassword(passwordEncoder.encode(companionData.getPassword()));
            savedCompanion.setAge(companionData.getAge());
            savedCompanion.setGender(companionData.getGender());
            savedCompanion.setName(companionData.getName());
            savedCompanion.setPhone(companionData.getPhone());
            savedCompanion.setRelative(companionData.getRelative());
            savedCompanion.setEmail(companionData.getEmail());
            return ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(savedCompanion)
                            .count(1L)
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
//        }else {
//            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
//            responseDto.setMessage(StatusMessage.NOT_FOUND);
//        }
    }

    //Delete Companion
    public ApiResponseDto deleteCompanion(Long companionId) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        if (this.companionExist(companionId)) {
            this.companionRepository.deleteById(companionId);
            responseDto = ApiResponseDto.builder()
                    .responseData(null)
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        } else {
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }
}
