package com.softeam.cfc.service;

import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;

public interface CollaborateurMailService {
	
	public void sendSimpleEmail(String email);
	
	public void sendEmail(String to,Context context) throws MessagingException ;

}
