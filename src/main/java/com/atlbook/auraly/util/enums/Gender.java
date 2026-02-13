package com.atlbook.auraly.util.enums;

public enum Gender {
    QADIN("Qadın"), KİŞİ("Kişi"), BAŞQA("Başqa");

    private String value;
    Gender (String value){
     this.value=value;
    }
    public String getValue(){
    return value;
    }
}
