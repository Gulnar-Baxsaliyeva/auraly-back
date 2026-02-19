package com.atlbook.auraly.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    String name;
    String email;
    String password;
}
