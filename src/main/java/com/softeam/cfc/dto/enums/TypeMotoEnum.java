package com.softeam.cfc.dto.enums;

public enum TypeMotoEnum {

	SCOOTER_THERMIQUE("ScooterThermique"), SCOOTER_ELECTRIQUE("ScooterElectrique"), MOINS250CM3("Moins250cm3"),
	PLUS250CM3("Plus250cm3");

	private final String value;

	TypeMotoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
