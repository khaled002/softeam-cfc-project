package com.softeam.cfc.dto.enums;

public enum GabaritEnum {
	
	CITADINE("Citadine"),
	SUV("SUV"),
	UTILITAIRE("Utilitaire"),
    BERLINE("Berline");
	
	private final String value;

	GabaritEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
