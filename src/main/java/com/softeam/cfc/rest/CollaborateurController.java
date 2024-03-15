package com.softeam.cfc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.service.EmpreinteCarboneService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/collaborateur")
@Log4j2
public class CollaborateurController {
	
	@Autowired
	EmpreinteCarboneService empreinteCarboneService;
	
	@PostMapping(value="/cfc")
	public ResponseEntity<String> calculateCarbonFootPrint(@RequestBody CarbonFootPrintFormDTO cfc)
	{
		
		double d = empreinteCarboneService.calculateDailyCarbonFootprintForTravel(cfc);
		
		double d1 = empreinteCarboneService.calculateDailyCarbonFootprintForHome(cfc);
		
		log.info("calculateDailyCarbonFootprintForTravel :  {}",d);
		log.info("calculateDailyCarbonFootprintForHome :  {}",d1);
		
		return ResponseEntity.ok().body("travel : "+d+"---- home"+d1);
		
	}



}
