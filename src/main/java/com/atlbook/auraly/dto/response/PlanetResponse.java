package com.atlbook.auraly.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanetResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long id;
    String name;
    String sign;
    Double fullDegree;
    String description;
}
