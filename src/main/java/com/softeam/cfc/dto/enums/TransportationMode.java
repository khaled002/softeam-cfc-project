package com.softeam.cfc.dto.enums;

public enum TransportationMode {
    BUS("Bus"),
    METRO("Metro"),
    VELO("Velo"),
    MOTO("Moto"),
    SCOOTER("Scooter"),
    TROTINETTE("Trotinette"),
    VOITURE("Voiture");

    private final String mode;

    TransportationMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
    
    public static TransportationMode fromValue(String value) {
        for (TransportationMode mode : TransportationMode.values()) {
            if (mode.getMode().equalsIgnoreCase(value)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Mode de transport inconnu: " + value);
    }
}
