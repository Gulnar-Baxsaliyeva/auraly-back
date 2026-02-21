package com.atlbook.auraly.service;

import com.atlbook.auraly.dao.entity.PlanetEntity;
import com.atlbook.auraly.dao.repository.ZodiacRepository;
import com.atlbook.auraly.dto.response.PlanetResponse;
import com.atlbook.auraly.util.helper.GetCurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetService {
    private final GetCurrentUser getCurrentUser;
    private final ZodiacRepository zodiacRepository;

    public List<PlanetResponse> getPlanets(){
        var user = getCurrentUser.getCurrentUser();
        var planets = user.getPlanetEntities().stream().map(this::entityToDto).toList();
        var zodiacs = zodiacRepository.findAll();
        for(var z : zodiacs){
            for(var p : planets){
                if(z.getZodiac().equals(p.getSign())){
                    p.setDescription(z.getDescription());
                }
            }
        }
        return planets;
    }

    private PlanetResponse entityToDto(PlanetEntity e){
        return PlanetResponse.builder()
                .name(e.getName())
                .sign(e.getZodiac())
                .build();
    }

    }
