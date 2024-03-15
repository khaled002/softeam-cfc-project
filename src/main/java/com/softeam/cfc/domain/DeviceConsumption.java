package com.softeam.cfc.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device_consumption")
public class DeviceConsumption {

	@Id
	@Column(name="device_type")
	private String deviceType;
	
	@Column(name="consumption_per_hour")
	private double consumptionPerHour;
	
	private String unit;

}
