package com.bfcai.ECH.controller;


import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.LoginRequestDTO;
import com.bfcai.ECH.entity.Patient;
import com.bfcai.ECH.service.PatientService;
import com.bfcai.ECH.wrapper.PatientWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    //Getting patient by id
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponseDto> getPatient(@PathVariable(name = "id") Long id) {
        ApiResponseDto<Patient> responseDto = patientService.FindById(id);
        return ResponseEntity.ok(responseDto);
    }


    //Add new patient
    @PostMapping(path = "/newPatient")
    public ResponseEntity<Patient> newPatient(@RequestBody Patient patient) {
        patientService.save(patient);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}/update")
    public ApiResponseDto updatePatient(@RequestBody PatientWrapper patientWrapper,@PathVariable Long id){
        return this.patientService.updatePatient(patientWrapper,id);
    }

    // Getting companions for patient
    @GetMapping(path = "/{patientId}/companions")
    public ApiResponseDto getCompanionsForPatient(@PathVariable Long patientId) {
        ApiResponseDto responseDto = this.patientService.findCompanionsForPatient(patientId);
        return responseDto;
    }


    //Getting medicine for patient
    @GetMapping(path = "/{patientId}/medicine")
    public ApiResponseDto getMedicineForPatient(@PathVariable Long patientId){
        ApiResponseDto responseDto=this.patientService.getAllMedicineForPatient(patientId);
        return responseDto;
    }


    //Getting doctors for patient
    @GetMapping(path = "/{patientId}/doctors")
    public ApiResponseDto getDoctorsForPatient(@PathVariable Long patientId){
        ApiResponseDto responseDto=this.patientService.getAllDoctorsForPatient(patientId);
        return responseDto;
    }

    //Delete patient
    @DeleteMapping(path = "/{patientId}/delete")
    public ApiResponseDto deletePatient(@PathVariable Long patientId){
        ApiResponseDto responseDto=this.patientService.deletePatient(patientId);
        return responseDto;
    }


    @PostMapping(path = "/login")
    public ApiResponseDto patientLogin(@RequestBody LoginRequestDTO loginRequestDTO){
        ApiResponseDto responseDto=this.patientService.authLogin(loginRequestDTO);
        return responseDto;
    }


}
