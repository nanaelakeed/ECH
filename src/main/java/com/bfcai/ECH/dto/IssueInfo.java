package com.bfcai.ECH.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class IssueInfo {
    private String description;
    private String name;
    private String treatment;
}
