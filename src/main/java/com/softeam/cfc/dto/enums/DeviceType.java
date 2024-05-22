package com.softeam.cfc.dto.enums;

public enum DeviceType {
    LAPTOP("laptop"),
    DESKTOP("desktop"),
    MONITOR("monitor"),
    PHONE("phone");

    private final String value;

    DeviceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DeviceType fromValue(String value) {
        for (DeviceType type : DeviceType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DeviceType value: " + value);
    }
}
