package com.atlbook.auraly.controller;

import com.atlbook.auraly.dto.response.PlanetResponse;
import com.atlbook.auraly.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/info")
public class PlanetController {
    private final PlanetService planetService;



    @GetMapping("/get")
    public List<PlanetResponse> getInfoZodiac(){
        return planetService.getPlanets();
    }
}
