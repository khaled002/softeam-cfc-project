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
@Table(name = "heating_emission")
public class HeatingEmission {
	
	
	@Id
	@Column(name="heating_type")
    private String heatingType;
	
	@Column(name="emission_per_day")
    private double emissionPerDay;
	
	private String unit;

}
