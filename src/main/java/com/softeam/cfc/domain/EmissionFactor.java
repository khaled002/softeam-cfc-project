package com.softeam.cfc.domain;

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
@Table(name = "emission_factor")
public class EmissionFactor {
	@Id
	private String id;
	
	private String type;
	
	private String subType;
	
	private String energy;
	
	private String carpooling;

	private double factor;
	
	private String unit;
}
