package com.softeam.cfc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.softeam.cfc.domain.CollabCarbonFootPrint;
import com.softeam.cfc.domain.Collaborateur;
import com.softeam.cfc.dto.CollaborateurDTO;
import com.softeam.cfc.dto.CollaborateurStatsDTO;
import com.softeam.cfc.dto.EmpreinteStatsDto;
import com.softeam.cfc.mapper.CollaborateurMapper;
import com.softeam.cfc.repository.CollabCarbonFootPrintRepository;
import com.softeam.cfc.repository.CollaborateurRepository;
import com.softeam.cfc.service.CollaborateurService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CollaborateurServiceImpl implements CollaborateurService {

	@Autowired
	CollaborateurRepository collaborateurRepository;
	
	@Autowired
	CollabCarbonFootPrintRepository collabCarbonFootPrintRepository;

	@Autowired
	private CollaborateurMapper mapper;

	public List<CollaborateurDTO> findAll() {
		List<Collaborateur> collaborateurs = collaborateurRepository.findAll();
		return mapper.toDTOs(collaborateurs);
	}

	@Override
	public CollaborateurDTO save(CollaborateurDTO collaborateurDTO) throws Exception {
		
		if(collaborateurRepository.findByEmail(collaborateurDTO.getEmail()).isEmpty())
		{
			Collaborateur c = collaborateurRepository.save(mapper.toEntity(collaborateurDTO));
			return mapper.toDTO(c);
			
		}else
		{
			throw new DuplicateKeyException(collaborateurDTO.getEmail()+" is already present in database !");
		}
	}

	@Override
	public CollaborateurDTO edit(CollaborateurDTO collaborateurDTO) throws Exception {
		
		Optional<Collaborateur> collabOpt  = collaborateurRepository.findById(Long.valueOf(collaborateurDTO.getId()));
		if(collabOpt.isPresent())
		{
			collaborateurRepository.save(mapper.toEntity(collaborateurDTO));
		}
		return collaborateurDTO;
	}

	@Override
	public void delete(String id) throws Exception {
		collaborateurRepository.deleteById(Long.valueOf(id));
		
	}

	@Override
	public List<String> deleteByIds(List<String> ids) throws Exception {
		// Retourne la liste des id qu'on a pas réussi a supprimé !
		 List<Long> idsLong = ids.stream()
	                .map(Long::parseLong)
	                .collect(Collectors.toList());
		collaborateurRepository.deleteAllByIdInBatch(idsLong);
		
		// on s'attend à ce que cette liste soit toujours vide sauf erreur
		List<Collaborateur> remaining = collaborateurRepository.findAllById(idsLong);
		return  remaining.stream()
		        .map(collaborateur -> String.valueOf(collaborateur.getId()))
		        .collect(Collectors.toList());
	}

	@Override
	public List<String> getListofEmails(List<String> ids) throws Exception {
		List<Long> idsLong = ids.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
		List<Collaborateur> collabs = collaborateurRepository.findAllById(idsLong);
		return collabs.stream().map(Collaborateur::getEmail).collect(Collectors.toList());
	}

	@Override
	public CollaborateurDTO findByEmail(String email) throws Exception {
		
		try
		{
		Optional<Collaborateur> collaborateurOpt =  collaborateurRepository.findByEmail(email);
		if(collaborateurOpt.isPresent())
			return mapper.toDTO(collaborateurOpt.get());
		
		
		return null;
		
		}catch(Exception e)
		{
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public EmpreinteStatsDto getCollaborateursStats() throws Exception {

		try {
			
			EmpreinteStatsDto empreinteStatsDto = new EmpreinteStatsDto();
			List<CollaborateurStatsDTO> collabStatsAnswered = new ArrayList<>();
		    List<CollaborateurStatsDTO> collabStatsDidNotAnswer = new ArrayList<>();
			
			List<CollabCarbonFootPrint> listCollabCarbonFootPrint = collabCarbonFootPrintRepository.findAll();
			List<Collaborateur> totalCollab = collaborateurRepository.findAll();

			empreinteStatsDto.setNbCollaborateursWhoAnswered(String.valueOf(listCollabCarbonFootPrint.size()));
			empreinteStatsDto.setNbCollaborateursWhoDidNotanswer(String.valueOf(totalCollab.size() - listCollabCarbonFootPrint.size()));
			
			
	       

	        // Créer un map pour associer les IDs des collaborateurs aux clients
	        Map<Long, String> map = listCollabCarbonFootPrint.stream().collect(Collectors.toMap(
	        		c -> c.getCollaborateur().getId(),
	        		c -> c.getClient().getNomClient()));


	        for (Collaborateur collaborateur : totalCollab) {
	            CollaborateurStatsDTO c = new CollaborateurStatsDTO();
	            c.setNom(collaborateur.getNom());
	            c.setPrenom(collaborateur.getPrenom());
	            c.setEmail(collaborateur.getEmail());
	            // Chercher le client à partir du map
	            c.setClient(map.get(collaborateur.getId()));
	            // Distribuer ensuite les données 
	            if (map.containsKey(collaborateur.getId())) {
	                collabStatsAnswered.add(c);
	            } else {
	                collabStatsDidNotAnswer.add(c);
	            }
	            
	        }
	        
	        empreinteStatsDto.setCollabStatsAnswered(collabStatsAnswered);
	        empreinteStatsDto.setCollabStatsDidNotAnswer(collabStatsDidNotAnswer);
			
			return empreinteStatsDto;
			

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BadRequestException(e.getMessage());
		}

	}
	
	

}
