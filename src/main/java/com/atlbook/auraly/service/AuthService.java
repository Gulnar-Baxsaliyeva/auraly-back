package com.atlbook.auraly.service;

import com.atlbook.auraly.Security.JwtUtil;
import com.atlbook.auraly.client.AstrologyClient;
import com.atlbook.auraly.dao.entity.PlanetEntity;
import com.atlbook.auraly.dao.entity.UserEntity;
import com.atlbook.auraly.dao.repository.PlanetRepository;
import com.atlbook.auraly.dao.repository.UserRepository;
import com.atlbook.auraly.dto.BirthRequest;
import com.atlbook.auraly.dto.LoginRequest;
import com.atlbook.auraly.dto.RegisterRequest;
import com.atlbook.auraly.dto.response.PlanetResponse;
import com.atlbook.auraly.util.enums.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final AstrologyClient astrologyClient;
    private final PlanetRepository planetRepository;


    @Transactional
    public void register(RegisterRequest request) {
        LocalDate birthData = request.getBirthDate();
        LocalTime birthTimeData = request.getBirthTime();

        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .birthDate(birthData)
                .birthTime(birthTimeData)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .role(UserRole.ROLE_USER)
                .gender(request.getGender())
                .build();

        var users = userRepository.save(user);

        int year = birthData.getYear();
        int month = birthData.getMonthValue();
        int day = birthData.getDayOfMonth();
        int hour = birthTimeData.getHour();
        int minute = birthTimeData.getMinute();
        var birthDto = BirthRequest.builder()
                .year(year)
                .month(month)
                .day(day)
                .hour(hour)
                .min(minute)
                .lat(user.getLatitude())
                .lon(user.getLongitude())
                .tzone(4)
                .build();

        var data = astrologyClient.getPlanets(birthDto);

        var planets = dtoToEntityList(data,users);


        planetRepository.saveAll(planets);

    }


    private PlanetEntity dtoToEntity(PlanetResponse dto, UserEntity e){
        return PlanetEntity.builder()
                .name(dto.getName())
                .zodiac(dto.getSign())
                .userEntity(e)
                .build();
    }

    private List<PlanetEntity> dtoToEntityList(List<PlanetResponse> dtos,UserEntity e){
        List<PlanetEntity> entities = new ArrayList<>();
        List<String> whiteList = List.of("Sun","Moon","Ascendant");
        List<Long> whiteIdList = List.of(0L,1L,9L);
        for(PlanetResponse r : dtos){
            if(!whiteIdList.contains(r.getId()) || !whiteList.contains(r.getName())) continue;
            entities.add(dtoToEntity(r,e));
        }
        return entities;
    }

    public String login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return jwtUtil.generatedToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );



        return User.builder().username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }


    // senin bu extrem methoduna ehtiyac yoxdur atiram commente baxarsan
//    private ZodiacSign calculateZodiac(LocalDate date) {
//        int day = date.getDayOfMonth();
//        int month = date.getMonthValue();
//
//        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) return ZodiacSign.QOÇ;
//        if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) return ZodiacSign.BUĞA;
//        if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) return ZodiacSign.ƏKİZLƏR;
//        if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) return ZodiacSign.XƏRÇƏNG;
//        if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) return ZodiacSign.ŞİR;
//        if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) return ZodiacSign.QIZ;
//        if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) return ZodiacSign.TƏRƏZİ;
//        if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) return ZodiacSign.ƏQRƏB;
//        if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) return ZodiacSign.OXATAN;
//        if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) return ZodiacSign.OĞLAQ;
//        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) return ZodiacSign.DOLÇA;
//        if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) return ZodiacSign.BALIQ;
//
//        throw new IllegalArgumentException("Invalid birth date");
//    }


}
