package com.atlbook.auraly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BirthRequest {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;
    private double lat;
    private double lon;
    private double tzone;
}
