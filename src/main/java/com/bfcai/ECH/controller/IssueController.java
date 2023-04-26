package com.bfcai.ECH.controller;


import com.bfcai.ECH.dao.IssueRepository;
import com.bfcai.ECH.dto.ApiResponseDto;
import com.bfcai.ECH.dto.IssueInfo;
import com.bfcai.ECH.dto.ResponseData;
import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ApiResponseDto getIssueById(@PathVariable Long id){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        responseDto =ApiResponseDto.builder()
                .responseData(ResponseData.builder()
                        .data(this.modelMapper.map(this.issueRepository.findById(id),IssueInfo.class))
                        .count(1L)
                        .build())
                .message(StatusMessage.SUCCESS)
                .code(StatusCode.SUCCESS.serverCode)
                .build();
        return responseDto;
    }
}
