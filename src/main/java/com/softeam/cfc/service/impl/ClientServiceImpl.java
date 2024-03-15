package com.softeam.cfc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softeam.cfc.domain.Client;
import com.softeam.cfc.repository.ClientRepository;
import com.softeam.cfc.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	ClientRepository clientRepository;

	@Override
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

}
