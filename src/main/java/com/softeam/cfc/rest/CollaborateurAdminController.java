package com.softeam.cfc.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softeam.cfc.dto.CollaborateurDTO;
import com.softeam.cfc.service.CollaborateurService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/collaborateurs/admin")
@Log4j2
public class CollaborateurAdminController {

	@Autowired
	private CollaborateurService collaborateurService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<CollaborateurDTO>> getAllCollaborateurs() {
		try {
			List<CollaborateurDTO> collaborateurs = collaborateurService.findAll();
			return ResponseEntity.ok().body(collaborateurs);
		} catch (Exception e) {
			log.error("Error occured while trying to get all collaborateurs : {}", e.getMessage());
			return ResponseEntity.internalServerError().body(new ArrayList<CollaborateurDTO>());
		}
	}

	@PutMapping(value = "/add")
	public ResponseEntity<CollaborateurDTO> addCollaborateur(@RequestBody CollaborateurDTO collaborateur) {

		try {
			return ResponseEntity.ok().body(collaborateurService.save(collaborateur));
		} catch (DuplicateKeyException e) {
			log.info("{} email is already present in db !", collaborateur.getEmail());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(collaborateur);

		} catch (Exception e) {
			log.error("Error occured while trying to add or update new collaborateur : {}, {}", collaborateur.getNom(),
					e.getMessage());
			return ResponseEntity.internalServerError().body(null);

		}
	}

	@PutMapping(value = "/edit")
	public ResponseEntity<CollaborateurDTO> editCollaborateur(@RequestBody CollaborateurDTO collaborateur) {

		try {
			return ResponseEntity.ok().body(collaborateurService.edit(collaborateur));

		} catch (Exception e) {
			log.error("Error occured while trying to add or update new collaborateur : {}, {}", collaborateur.getNom(),
					e.getMessage());
			return ResponseEntity.internalServerError().body(null);

		}
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteCollaborateur(@PathVariable String id) {
		try {
			collaborateurService.delete(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
		} catch (Exception e) {
			log.error("Error occured while trying to  delete collaborateur with id : {}, {}", id, e.getMessage());
			return ResponseEntity.internalServerError().body(null);

		}
	}
	
	@DeleteMapping(value = "/delete/all")
	public ResponseEntity<List<String>> deleteCollaborateur(@RequestParam List<String> ids) {
		try {
			List<String> remaining = collaborateurService.deleteByIds(ids);
			return ResponseEntity.ok().body(remaining);
			
		} catch (Exception e) {
			log.error("Error occured while trying to  delete collaborateurs {}", e.getMessage());
			return ResponseEntity.internalServerError().body(null);

		}
	}

}
