package com.atlbook.auraly.client;

import com.atlbook.auraly.dto.BirthRequest;
import com.atlbook.auraly.dto.response.PlanetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "astrologyClient",
        url = "https://json.astrologyapi.com"
)
public interface AstrologyClient {

    @PostMapping("/v1/planets")
    List<PlanetResponse> getPlanets(@RequestBody BirthRequest request);
}
