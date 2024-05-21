package com.softeam.cfc.dto.enums;

public enum MoyenTransport {
	
	VOITURE("Voiture"),
	VELO("Vélo"),
	TROTINETTE("Trotinette"),
	BUS("Bus"),
	MOTO("Moto"),
	METRO("Métro");
	
	private final String value;

	MoyenTransport(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	

}
