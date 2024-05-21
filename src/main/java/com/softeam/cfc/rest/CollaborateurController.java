package com.softeam.cfc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.service.EmpreinteCarboneService;

@RestController
@RequestMapping("/api/collaborateur")
public class CollaborateurController {
	
	@Autowired
	EmpreinteCarboneService empreinteCarboneService;
	
	@PostMapping(value="/cfc")
	public ResponseEntity<String> calculateCarbonFootPrint(@RequestBody CarbonFootPrintFormDTO cfc)
	{
		double d = empreinteCarboneService.calculateDailyCarbonFootprintForOffice(cfc);
		return ResponseEntity.ok(""+d);
		
	}



}
