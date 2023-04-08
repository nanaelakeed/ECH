package com.bfcai.ECH.wrapper;


import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DoctorWrapper {

    private String name;

    private String phone;

    private String location;

}
