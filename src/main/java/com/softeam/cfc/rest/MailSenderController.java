package com.softeam.cfc.rest;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softeam.cfc.service.CollaborateurMailService;
import com.softeam.cfc.service.CollaborateurService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/collaborateurs/admin/mails")
@Log4j2
public class MailSenderController {
	
	
	@Autowired
	CollaborateurMailService mailService;
	
	
	@Autowired
	private CollaborateurService collaborateurService;
	
	/**
	 * TODO Renvoyer réponse adéquoite 
	 */
	@PostMapping(value = "/list")
	public ResponseEntity<String> EnvoyerMailList(@RequestBody List<String> ids) {
		try {
			
			List<String> emails = collaborateurService.getListofEmails(ids);
			emails.forEach(email -> {
				mailService.sendSimpleEmail(email);
				
			});
			
			return ResponseEntity.ok().body(Strings.EMPTY);
		} catch (Exception e) {
			log.error("Error occured while trying to get all collaborateurs : {}", e.getMessage());
			return ResponseEntity.internalServerError().body(Strings.EMPTY);
		}
	}
}
