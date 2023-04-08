package com.bfcai.ECH.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseData<T> {

    Long count;

    T data;

}
