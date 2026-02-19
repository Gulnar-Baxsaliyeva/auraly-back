package com.atlbook.auraly.controller;

import com.atlbook.auraly.dto.LoginRequest;
import com.atlbook.auraly.dto.RegisterRequest;
import com.atlbook.auraly.dto.SignUpRequest;
import com.atlbook.auraly.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auraly")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok("Qeydiyyatın ilk mərhələsi uğurludur");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/complete-profile")
    public ResponseEntity<String> completeProfile(@RequestBody RegisterRequest request) {

        authService.completeProfile(request);
        return ResponseEntity.ok("Profil məlumatları uğurla tamamlandı!");
    }
}