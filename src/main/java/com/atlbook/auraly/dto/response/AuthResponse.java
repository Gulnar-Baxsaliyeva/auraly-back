package com.atlbook.auraly.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
@AllArgsConstructor
public class AuthResponse {
    String accessToken;
}
