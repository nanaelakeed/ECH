package com.bfcai.ECH.wrapper;


import com.bfcai.ECH.type.GenderType;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientWrapper {
    private String name;

    private int age;

    private String email;

    private String password;

    private String diseases;

    private GenderType gender;

    private String phone;
}
