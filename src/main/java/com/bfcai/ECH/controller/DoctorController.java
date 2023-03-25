package com.bfcai.ECH.controller;


import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.entity.Doctor;
import com.bfcai.ECH.service.DoctorService;
import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping(path = "/newDoctor")
    public ApiResponseDto saveDoctor(@RequestBody Doctor doctor){
        Doctor savedDoctor=(Doctor) this.doctorService.saveDoctor(doctor).getResponseData().getData();
        this.doctorService.savePatientDoctor(savedDoctor);
        return ApiResponseDto.builder().responseData(ResponseData.builder()
                        .data(savedDoctor)
                .build())
                .message(StatusMessage.SUCCESS)
                .code(StatusCode.SUCCESS.serverCode)
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ApiResponseDto deleteById(@PathVariable Long id){
        return this.doctorService.deleteDoctor(id);
    }

    @GetMapping(path = "/{id}/allPatients")
    public ApiResponseDto getAllDoctorPatients(@PathVariable Long id){
        return this.doctorService.getAllDoctorPatients(id);
    }
}
