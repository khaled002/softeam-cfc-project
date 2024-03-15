package com.softeam.cfc.dto.enums;

public enum HeatingType {
    COLLECTIF("Chauffage collectif"),
    ELECTRIQUE("Chauffage électrique"),
    GAZ("Chauffage gaz"),
    POMPE("Pompe à chaleur"),
    BOIS("Chauffage au bois"),
    CHAUDIERE("Chaudière à condensation");

    private final String value;

    HeatingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HeatingType fromValue(String value) {
        for (HeatingType type : HeatingType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid HeatingType value: " + value);
    }
}

