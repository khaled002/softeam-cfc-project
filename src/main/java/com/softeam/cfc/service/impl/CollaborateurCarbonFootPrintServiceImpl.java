package com.softeam.cfc.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softeam.cfc.domain.Client;
import com.softeam.cfc.domain.CollabCarbonFootPrint;
import com.softeam.cfc.domain.Collaborateur;
import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.repository.ClientRepository;
import com.softeam.cfc.repository.CollabCarbonFootPrintRepository;
import com.softeam.cfc.repository.CollaborateurRepository;
import com.softeam.cfc.service.CollaborateurCarbonFootPrintService;


@Service
public class CollaborateurCarbonFootPrintServiceImpl implements CollaborateurCarbonFootPrintService {
	
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	CollaborateurRepository collaborateurRepository;
	
	@Autowired
	CollabCarbonFootPrintRepository collabCarbonFootPrintRepository;

	@Override
	public void addColloboraeurCarbonFootPrint(CarbonFootPrintFormDTO carbonFootPrintFormDTO, String carbonFootPrint) {
		
		Client client = clientRepository.findByNomClient(carbonFootPrintFormDTO.getClient());
		Optional<Collaborateur> collaborateurOpt = collaborateurRepository.findByEmail(carbonFootPrintFormDTO.getEmail());
		CollabCarbonFootPrint collabCarbonFootPrint = new CollabCarbonFootPrint();
		
		collabCarbonFootPrint.setCollaborateur(collaborateurOpt.get());
		collabCarbonFootPrint.setClient(client);
		collabCarbonFootPrint.setCarbonFootprint(carbonFootPrint);
		
		collabCarbonFootPrintRepository.save(collabCarbonFootPrint);
	}
	
	
	
	
	
	

}
