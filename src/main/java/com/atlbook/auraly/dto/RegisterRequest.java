package com.atlbook.auraly.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class RegisterRequest {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String username;
    @NotBlank
    String password;
    @Email
    String email;
    @NotBlank
    String phone;
    @NotNull
    LocalDate birthDate;
    @NotNull
    LocalTime birthTime;
}
