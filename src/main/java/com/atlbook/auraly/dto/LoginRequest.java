package com.atlbook.auraly.dto;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
