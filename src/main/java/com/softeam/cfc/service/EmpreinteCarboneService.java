package com.softeam.cfc.service;

import com.softeam.cfc.dto.CarbonFootPrintFormDTO;

public interface EmpreinteCarboneService {

	double calculateDailyCarbonFootprintForOffice(CarbonFootPrintFormDTO cfc);

}
