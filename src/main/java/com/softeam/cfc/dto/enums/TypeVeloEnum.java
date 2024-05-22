package com.softeam.cfc.dto.enums;

public enum TypeVeloEnum {

	ELECTRIC_ASSISTANCE("ElecAssist"), STANDARD("Standard");

	private final String value;

	TypeVeloEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
