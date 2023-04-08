package com.bfcai.ECH.dto;


import com.bfcai.ECH.type.StatusCode;
import com.bfcai.ECH.type.StatusMessage;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ApiResponseDto<T> {

    StatusMessage message;

    Long code;

    ResponseData<T> responseData;
}
