package com.softeam.cfc.service;

import com.softeam.cfc.dto.CarbonFootPrintFormDTO;

public interface EmpreinteCarboneService {

	double calculateDailyCarbonFootprintForHome(CarbonFootPrintFormDTO cfc);

	double calculateDailyCarbonFootprintForTravel(CarbonFootPrintFormDTO cfc);

}
