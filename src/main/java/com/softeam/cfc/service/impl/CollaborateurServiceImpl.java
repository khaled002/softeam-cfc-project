package com.softeam.cfc.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.softeam.cfc.domain.Collaborateur;
import com.softeam.cfc.dto.CollaborateurDTO;
import com.softeam.cfc.mapper.CollaborateurMapper;
import com.softeam.cfc.repository.CollaborateurRepository;
import com.softeam.cfc.service.CollaborateurService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CollaborateurServiceImpl implements CollaborateurService {

	@Autowired
	CollaborateurRepository collaborateurRepository;

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

}
