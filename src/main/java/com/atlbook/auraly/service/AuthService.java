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
import com.atlbook.auraly.dto.SignUpRequest;
import com.atlbook.auraly.dto.response.PlanetResponse;
import com.atlbook.auraly.util.enums.UserRole;
import com.atlbook.auraly.util.helper.GetCurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AstrologyClient astrologyClient;
    private final PlanetRepository planetRepository;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    private final GetCurrentUser getCurrentUser;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));
        return buildUserDetails(user);
    }

    public UserDetails loadUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(UserEntity user) {
        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }

    public void signUp(SignUpRequest request) {
        UserEntity user = UserEntity.builder()
                .firstName(request.getName())
                .email(request.getEmail())
                .role(UserRole.ROLE_USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return jwtUtil.generatedToken(user);
    }

    @Transactional
    public void completeProfile(RegisterRequest request) {
        var user = getCurrentUser.getCurrentUser();
        UserEntity existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setUsername(request.getUsername());
        existingUser.setPhone(request.getPhone());
        existingUser.setBirthDate(request.getBirthDate());
        existingUser.setBirthTime(request.getBirthTime());
        existingUser.setLatitude(request.getLatitude());
        existingUser.setLongitude(request.getLongitude());
        existingUser.setLocation(request.getLocation());
        existingUser.setGender(request.getGender());
        existingUser.setWorkStatus(request.getWorkStatus());
        var users = userRepository.save(existingUser);


        LocalDate birthData = request.getBirthDate();
        LocalTime birthTimeData = request.getBirthTime();
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
        for(var d  : data){
            String planetName = planetNameTranslete(d);
            d.setName(planetName);
            String signZodiacTranslate = zodiacTranslete(d);
            d.setSign(signZodiacTranslate.isEmpty() ? d.getSign() : signZodiacTranslate);
        }
        var planets = dtoToEntityList(data, users);
        planetRepository.deleteByUserEntity(users);
        planetRepository.saveAll(planets);
    }

    private String planetNameTranslete(PlanetResponse d) {
        String planetNameTranslete;
        switch (d.getName()){
            case "Sun" -> planetNameTranslete = "Günəş";
            case "Moon" -> planetNameTranslete = "Ay";
            case "Ascendant" -> planetNameTranslete = "Yüksələn";
            default -> planetNameTranslete = "";
        }
        return planetNameTranslete;
    }

    private static String zodiacTranslete(PlanetResponse d) {
        String signZodiacTranslate;
        switch (d.getSign()){
            case "Cancer" -> signZodiacTranslate = "Xərçəng";
            case "Aries" -> signZodiacTranslate = "Qoç";
            case "Taurus" -> signZodiacTranslate = "Buğa";
            case "Gemini" -> signZodiacTranslate = "Əkizlər";
            case "Leo" -> signZodiacTranslate = "Şir";
            case "Virgo" -> signZodiacTranslate = "Qız";
            case "Libra" -> signZodiacTranslate = "Tərəzi";
            case "Scorpio" -> signZodiacTranslate = "Əqrəb";
            case "Sagittarius" -> signZodiacTranslate = "Oxatan";
            case "Capricorn" -> signZodiacTranslate = "Oğlaq";
            case "Aquarius" -> signZodiacTranslate = "Dolça";
            case "Pisces" -> signZodiacTranslate = "Balıq";
            default -> signZodiacTranslate = "";
        }
        return signZodiacTranslate;
    }


    private PlanetEntity dtoToEntity(PlanetResponse dto, UserEntity e) {
        return PlanetEntity.builder()
                .name(dto.getName())
                .zodiac(dto.getSign())
                .userEntity(e)
                .build();
    }

    private List<PlanetEntity> dtoToEntityList(List<PlanetResponse> dtos, UserEntity e) {
        List<PlanetEntity> entities = new ArrayList<>();
        List<String> whiteList = List.of("Günəş", "Ay", "Yüksələn");
        for (PlanetResponse r : dtos) {
            if (!whiteList.contains(r.getName())) continue;
            entities.add(dtoToEntity(r, e));
        }
        return entities;
    }

}
