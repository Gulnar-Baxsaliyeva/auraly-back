package com.atlbook.auraly.dto;

import com.atlbook.auraly.util.enums.Gender;
import com.atlbook.auraly.util.enums.WorkStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("soyad")
    String lastName;
    String location;


    @JsonProperty("istifadəçi_adı")
    String username;

    @JsonProperty("əlaqə_nömrəsi")
    String phone;

    @NotNull
    @JsonProperty("doğum_tarixi")
    LocalDate birthDate;

    Double latitude;
    Double longitude;

    @NotNull
    @JsonProperty("doğum_saatı")
    LocalTime birthTime;

    @NotNull
    @JsonProperty("cins")
    Gender gender;

    @NotNull
    @JsonProperty("iş_statusu")
    WorkStatus workStatus;

}
