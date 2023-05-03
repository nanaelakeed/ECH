package com.bfcai.ECH.controller;

import com.bfcai.ECH.dao.CompanionRepository;
import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.entity.Companion;
import com.bfcai.ECH.service.CompanionService;
import com.bfcai.ECH.type.StatusMessage;
import com.bfcai.ECH.util.LogUtil;
import com.bfcai.ECH.wrapper.CompanionWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/companions")
@RequiredArgsConstructor
public class CompanionController {

    private final CompanionService companionService;


    @PostMapping
    public ApiResponseDto saveCompanion(@RequestBody Companion companion) {
        Companion savedCompanion=(Companion) this.companionService.saveCompanion(companion).getResponseData().getData();
        this.companionService.savePatientCompanion(savedCompanion);
        return ApiResponseDto.builder().message(StatusMessage.SUCCESS).build();
    }

    //Getting specific companion
    @GetMapping(path = "/{companionId}/specificCompanion")
    public ApiResponseDto getSpecificCompanion(@PathVariable Long companionId){
        ApiResponseDto responseDto=this.companionService.getSpecificCompanion(companionId);
        return responseDto;
    }

    //Getting all patients for companion
    @GetMapping(path = "/{companionId}/companionPatients")
    public ApiResponseDto getAllPatientsForCompanion(@PathVariable Long companionId){
        return this.companionService.getAllPatientsForCompanion(companionId);
    }

    //Delete companion
    @DeleteMapping(path = "/{companionId}")
    public ApiResponseDto deleteCompanion(@PathVariable Long companionId){
        return this.companionService.deleteCompanion(companionId);
    }

    //Update Companion
    @PutMapping(path = "{companionId}/update")
    public ApiResponseDto updateCompanion(@RequestBody CompanionWrapper companion, @PathVariable Long companionId){
        return this.companionService.updateCompanion(companion,companionId);
    }
}
