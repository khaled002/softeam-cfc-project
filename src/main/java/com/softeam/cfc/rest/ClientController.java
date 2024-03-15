package com.softeam.cfc.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softeam.cfc.domain.Client;
import com.softeam.cfc.service.ClientService;


@RestController
@RequestMapping("/api/clients/")
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	@GetMapping(value="/list")
	public ResponseEntity<List<String>> getAllClients()
	{
		return ResponseEntity.ok().body(clientService.getAllClients().stream().map(Client::getNomClient).collect(Collectors.toList()));
	}

}
