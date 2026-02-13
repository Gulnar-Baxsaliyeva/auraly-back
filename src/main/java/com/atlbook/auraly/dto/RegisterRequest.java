package com.atlbook.auraly.dto;

import com.atlbook.auraly.util.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class RegisterRequest {
    @NotBlank
    @JsonProperty("ad")
    String firstName;

    @NotBlank
    @JsonProperty("soyad")
    String lastName;

    @NotBlank
    @JsonProperty("istifadəçi_adı")
    String username;


    @NotBlank
    @JsonProperty("şifrə")
    String password;

    @Email
    String email;

    @NotBlank
    @JsonProperty("əlaqə_nömrəsi")
    String phone;

    @NotNull
    @JsonProperty("doğum_tarixi")
    LocalDate birthDate;

    @NotNull
    Double latitude;
    @NotNull
    Double longitude;

    @NotNull
    @JsonProperty("doğum_saatı")
    LocalTime birthTime;

 @NotBlank
    @JsonProperty("cins")
    Gender gender;

}
