package com.bfcai.ECH.wrapper;

import com.bfcai.ECH.type.GenderType;
import jakarta.persistence.Column;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanionWrapper {

    private String name;

    private int age;

    private String email;

    private String password;

    private GenderType gender;

    private String phone;

    private String relative;
}
