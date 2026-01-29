package com.atlbook.auraly.enums;

public enum ZodiacSign {
    ARIES("Qoç"),
    TAURUS("Buğa"),
    GEMINI("Əkizlər"),
    CANCER("Xərçəng"),
    LEO("Şir"),
    VIRGO("Qız"),
    LIBRA("Tərəzi"),
    SCORPIO("Əqrəb"),
    SAGITTARIUS("Oxatan"),
    CAPRICORN("Oğlaq"),
    AQUARIUS("Dolça"),
    PISCES("Balıq");

    private String value;
    ZodiacSign(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }


}
