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
    String description;
}
// frontlardan sorus gor location melumatlarindan longitude latitude  goture bilir ? o nedirki sorus sen
// ne ucundu mahiyyetin bilim qrupa yazim sorusum region melumati ucun lazimdir ios androidden sorusum he? he wp bax