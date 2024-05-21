package com.softeam.cfc.dto.enums;

public enum TypeEnergieEnum {
	
	ELECTRIQUE("Électrique"),
	DIESEL("Diesel"),
	GPL("GPL"),
	ESSENCE("Essence"),
	HYBRIDE("Hybride");
	
	private final String value;

	TypeEnergieEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}


}
