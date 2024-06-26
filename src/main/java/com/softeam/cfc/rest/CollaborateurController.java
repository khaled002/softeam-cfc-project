package com.softeam.cfc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.dto.CollaborateurDTO;
import com.softeam.cfc.dto.EmpreinteCarboneDto;
import com.softeam.cfc.dto.EmpreinteStatsDto;
import com.softeam.cfc.service.CollaborateurService;
import com.softeam.cfc.service.EmpreinteCarboneService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/collaborateur")
@Slf4j
public class CollaborateurController {
	
	@Autowired
	EmpreinteCarboneService empreinteCarboneService;
	
	@Autowired
	CollaborateurService collaborateurService;
	
	@PostMapping(value="/cfc")
	public ResponseEntity<EmpreinteCarboneDto> calculateCarbonFootPrint(@RequestBody CarbonFootPrintFormDTO cfc)
	{
		EmpreinteCarboneDto e  = empreinteCarboneService.calculateDailyCarbonFootprintForOffice(cfc);
		return ResponseEntity.ok(e);
		
	}
	
	@GetMapping(value="/email/{email}")
	public ResponseEntity<CollaborateurDTO> getCollaborateurByEmail(@PathVariable String email)
	{
	
		try {
			CollaborateurDTO c = collaborateurService.findByEmail(email);
			return ResponseEntity.ok().body(c);
		} catch (Exception e) {
			log.error("Error occured while trying to get  collaborateur : {}", e.getMessage());
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping(value="/stats")
	public ResponseEntity<EmpreinteStatsDto> getCollaborateurStats()
	{
		try {
			EmpreinteStatsDto dto = collaborateurService.getCollaborateursStats();
			return ResponseEntity.ok().body(dto);

		} catch (Exception e) {
			log.error("Error occured while trying to get  collaborateur : {}", e.getMessage());
			return ResponseEntity.internalServerError().body(null);
		}
	}



}
