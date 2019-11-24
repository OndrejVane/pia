package com.vane.pia.model;

public enum Roles {
    ADMIN("ADMIN", "Admins"),
    USER("USER", "Users"),
    SECRETARY("SECRETARY", "Secretaries"),
    ACCOUNTANT("ACCOUNTANT", "Accountants");


    private final String code;
    private final String name;

    Roles(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
