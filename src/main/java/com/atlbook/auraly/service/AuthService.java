package com.atlbook.auraly.service;

import com.atlbook.auraly.Security.JwtUtil;
import com.atlbook.auraly.dao.entity.UserEntity;
import com.atlbook.auraly.dao.repository.UserRepository;
import com.atlbook.auraly.dto.LoginRequest;
import com.atlbook.auraly.dto.RegisterRequest;
import com.atlbook.auraly.dto.response.AuthResponse;
import com.atlbook.auraly.enums.UserRole;
import com.atlbook.auraly.enums.ZodiacSign;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {
@Autowired
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;


    public void register(RegisterRequest request) {

        ZodiacSign zodiac = calculateZodiac(request.getBirthDate());
        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .birthTime(request.getBirthTime())
                .zodiacSign(zodiac)
                .role(UserRole.ROLE_USER)
                .build();
        userRepository.save(user);
    }


    public String login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return jwtUtil.generatedToken(user);
    }


    private ZodiacSign calculateZodiac(LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) return ZodiacSign.ARIES;
        if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) return ZodiacSign.TAURUS;
        if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) return ZodiacSign.GEMINI;
        if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) return ZodiacSign.CANCER;
        if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) return ZodiacSign.LEO;
        if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) return ZodiacSign.VIRGO;
        if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) return ZodiacSign.LIBRA;
        if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) return ZodiacSign.SCORPIO;
        if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) return ZodiacSign.SAGITTARIUS;
        if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) return ZodiacSign.CAPRICORN;
        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) return ZodiacSign.AQUARIUS;
        if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) return ZodiacSign.PISCES;

        throw new IllegalArgumentException("Invalid birth date");
    }
}
