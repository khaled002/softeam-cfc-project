package com.softeam.cfc.dto;

import com.softeam.cfc.dto.enums.HeatingType;
import com.softeam.cfc.dto.enums.TransportationMode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarbonFootPrintFormDTO {

	private Client client;
	private int presenceDays;
	private TransportationMode transportationMode;
	private int distanceKm;
	private String housingType;
	private HomeEquipment homeEquipment;
	private HeatingType heatingType;

	@Getter
	@Setter
	public static class Client {
		private String id;
		private String name;

	}

	@Getter
	@Setter
	public static class HomeEquipment {
		private int laptop;
		private int desktopComputer;
		private int monitor;
		private int phone;

	}

}
