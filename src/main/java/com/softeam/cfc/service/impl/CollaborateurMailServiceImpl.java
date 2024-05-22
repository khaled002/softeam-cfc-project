package com.softeam.cfc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.softeam.cfc.service.CollaborateurMailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@AllArgsConstructor
@Service
public class CollaborateurMailServiceImpl implements CollaborateurMailService {

	@Autowired
	TemplateEngine templateEngine;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public void sendSimpleEmail(String email) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setFrom("kais.benhajamor@softeam.fr");
		sm.setTo(email);
		sm.setSubject("");
		sm.setText("");
		
		javaMailSender.send(sm);
		
	}

	@Override
	public void sendEmail(String to, Context context) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String htmlContent = templateEngine.process("emailTemplate", context);

        helper.setTo(to);
        helper.setSubject("Formulaire d'empreinte carbone");
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
		
	}


}
