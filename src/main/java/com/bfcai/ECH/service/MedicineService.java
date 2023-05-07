package com.bfcai.ECH.service;


import com.bfcai.ECH.dao.MedicineRepository;
import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.entity.Medicine;
import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import com.bfcai.ECH.wrapper.MedicineWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;

    public ApiResponseDto saveMedicine(Medicine medicine){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        responseDto=ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(this.modelMapper.map(this.medicineRepository.save(medicine),MedicineWrapper.class))
                            .count(1L)
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        return responseDto;
    }


    //Getting medicine by patient id
    public ApiResponseDto findMedicineByPatientId(Long patientId){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        List<MedicineWrapper> medicine=this.medicineRepository.findAllByPatientId(patientId)
                .stream()
                .map(medicine1 -> this.modelMapper.map(medicine1,MedicineWrapper.class))
                .collect(Collectors.toList());
        if(medicine!=null){
            responseDto=ApiResponseDto.builder()
                    .responseData(ResponseData.builder()
                            .data(medicine)
                            .count((long) medicine.size())
                            .build())
                    .code(StatusCode.SUCCESS.serverCode)
                    .message(StatusMessage.SUCCESS)
                    .build();
        }else {
            responseDto.setMessage(StatusMessage.NOT_FOUND);
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
        }
        return responseDto;
    }

    //Delete By Id
    public ApiResponseDto deleteByMedicineId(Long id){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        Medicine medicine=this.medicineRepository.findById(id).orElse(null);
        if(medicine!=null){
            this.medicineRepository.deleteById(id);
            responseDto.setMessage(StatusMessage.SUCCESS);
            responseDto.setCode(StatusCode.SUCCESS.serverCode);
        }else {
            responseDto.setMessage(StatusMessage.NOT_FOUND);
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
        }
        return responseDto;
    }


    //Delete By patient Id
    public ApiResponseDto deleteByPatientId(Long patientId){
        ApiResponseDto responseDto=findMedicineByPatientId(patientId);
        if(responseDto.getResponseData()!=null){
            List<Medicine> medicines= this.medicineRepository.findAllByPatientId(patientId);
            for (Medicine m : medicines){
                this.medicineRepository.deleteById(m.getId());
            }
        }
        else {
            responseDto.setMessage(StatusMessage.NOT_FOUND);
            responseDto.setCode(StatusCode.NOT_FOUND.serverCode);
        }
        return responseDto;
    }
}
