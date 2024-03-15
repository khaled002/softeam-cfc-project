package com.softeam.cfc.config;

public enum CacheEnumKey {

	EMISSION_FACTOR("emissionFactor_"), DEVICE_CONSUMPTION("deviceConsumption_"), HEATING_EMISSION("heatingEmission_");

	private final String value;

	CacheEnumKey(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
