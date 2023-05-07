package com.bfcai.ECH.controller;


import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.entity.Medicine;
import com.bfcai.ECH.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    @Transactional
    public ApiResponseDto saveMedicine(@RequestBody Medicine medicine){
       return this.medicineService.saveMedicine(medicine);
    }

    @GetMapping(path = "/{patientId}/medicineByPatientId")
    public ApiResponseDto findMedicineByPatientId(@PathVariable Long patientId){
        ApiResponseDto responseDto=this.medicineService.findMedicineByPatientId(patientId);
        return responseDto;
    }

    @DeleteMapping(path = "/{patientId}")
    public ApiResponseDto deleteByPatientId(@PathVariable Long patientId){
        ApiResponseDto responseDto=this.medicineService.deleteByPatientId(patientId);
        return responseDto;
    }

    @DeleteMapping(path = "/{medicineId}/deleteByMedicineId")
    public  ApiResponseDto deleteByMedicineId(@PathVariable Long medicineId){
        return this.medicineService.deleteByMedicineId(medicineId);
    }
}
