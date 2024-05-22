package com.softeam.cfc.service;

import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.dto.EmpreinteCarboneDto;

public interface EmpreinteCarboneService {

	EmpreinteCarboneDto calculateDailyCarbonFootprintForOffice(CarbonFootPrintFormDTO cfc);

}
