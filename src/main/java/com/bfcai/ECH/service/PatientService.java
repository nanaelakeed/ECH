package com.bfcai.ECH.service;


import com.bfcai.ECH.dao.PatientRepository;
import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.LoginRequestDTO;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.entity.Companion;
import com.bfcai.ECH.entity.Patient;
import com.bfcai.ECH.exception.ConflictException;
import com.bfcai.ECH.exception.NotFoundException;
import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import com.bfcai.ECH.wrapper.CompanionWrapper;
import com.bfcai.ECH.wrapper.DoctorWrapper;
import com.bfcai.ECH.wrapper.MedicineWrapper;
import com.bfcai.ECH.wrapper.PatientWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final MedicineService medicineService;
    private final PasswordEncoder passwordEncoder;

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
                                    .data(this.modelMapper.map(fetchedPatient,PatientWrapper.class))
                                    .count(1L)
                                    .build()
                    )
                    .build();

        } else {

            responseDto = ApiResponseDto
                    .builder()
                    .responseData(null)
                    .message(StatusMessage.NOT_FOUND)
                    .build();
        }
        return responseDto;
    }


    public ApiResponseDto<List<Companion>> findCompanionsForPatient(Long id) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        if (!this.validatePatientData(this.patientRepository.findById(id).orElse(null))) {
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
        if (!this.validatePatientData(patient)) {
            throw new ConflictException("Email or phone already existed");
        } else {
            patient.setPassword(this.passwordEncoder.encode(patient.getPassword()));
            return ApiResponseDto.builder()
                    .responseData(
                            ResponseData.builder()
                                    .data(this.modelMapper.map(this.patientRepository.save(patient),PatientWrapper.class))
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
        Patient patient = this.patientRepository.findById(patientId).orElse(null);
        if (patient != null) {
            this.medicineService.deleteByPatientId(patientId);
            this.patientRepository.deleteById(patientId);
        } else {
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }


    @Transactional
    public ApiResponseDto updatePatient(PatientWrapper patientWrapper,Long id){
        Patient savedPatient=this.patientRepository.findById(id).orElseThrow(()->new NotFoundException("No Patient with id"+id));
        savedPatient.setPassword(passwordEncoder.encode(patientWrapper.getPassword()));
        savedPatient.setAge(patientWrapper.getAge());
        savedPatient.setEmail(patientWrapper.getEmail());
        savedPatient.setGender(patientWrapper.getGender());
        savedPatient.setName(patientWrapper.getName());
        savedPatient.setDiseases(patientWrapper.getDiseases());
        savedPatient.setPhone(patientWrapper.getPhone());
        return ApiResponseDto.builder()
                .responseData(ResponseData.builder()
                        .data(this.modelMapper.map(savedPatient,PatientWrapper.class))
                        .count(1L)
                        .build())
                .code(StatusCode.SUCCESS.serverCode)
                .message(StatusMessage.SUCCESS)
                .build();
    }

    public ApiResponseDto authLogin(LoginRequestDTO loginRequestDTO) {
        Patient patient = this.patientRepository.findPatientByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new NotFoundException("No user with this email found"));
        return ApiResponseDto
                .builder()
                .responseData(
                        ResponseData
                                .builder()
                                .data(passwordEncoder.matches(loginRequestDTO.getPassword(), patient.getPassword()))
                                .build()
                )
                .build();
    }
}
