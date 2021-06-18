package ru.pfr.role_enum;

public enum ROLE_ENUM {
    ROLE_DOWNLOAD ("ROLE_DOWNLOAD"),
    ROLE_UPDATE ("ROLE_UPDATE"),
    ROLE_ADMIN ("ROLE_ADMIN");

    private String code;
    private ROLE_ENUM(String code) {
        this.code = code;
    }

    public String getString(){ return code;}
}
