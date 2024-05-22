package com.softeam.cfc.service;

import java.util.List;

import com.softeam.cfc.dto.CollaborateurDTO;
import com.softeam.cfc.dto.CollaborateurStatsDTO;
import com.softeam.cfc.dto.EmpreinteStatsDto;

public interface CollaborateurService {
	
	
	public List<CollaborateurDTO> findAll();
	
	public CollaborateurDTO save(CollaborateurDTO collaborateurDTO) throws Exception;
	
	public CollaborateurDTO edit(CollaborateurDTO collaborateurDTO) throws Exception;
	
	public void delete(String id) throws Exception;
	
	public List<String> deleteByIds(List<String> ids) throws Exception;
	
	public List<String> getListofEmails(List<String> ids) throws Exception;
	
	public CollaborateurDTO findByEmail(String email) throws Exception;
	
	public EmpreinteStatsDto getCollaborateursStats() throws Exception;

}
