package com.softeam.cfc.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarbonFootPrintFormDTO {
	
		private String email;
	 	private String client;
	    private String presenceDays;
	    private String housingType;
	    private String heatingType;
	    private String laptop;
	    private String desktop;
	    private String monitor;
	    private String phone;
	    private List<LocomotionDto> locomotions;
}
