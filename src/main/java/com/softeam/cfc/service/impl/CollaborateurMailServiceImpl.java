package com.softeam.cfc.service.impl;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.softeam.cfc.mail.MailMessage;
import com.softeam.cfc.service.CollaborateurMailService;

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
	public void sendWelcomeEmail(String email) {
	        Context context = new Context();
	        context.setVariable("name", "Zouaoui");
	        context.setVariable("email", email);

	        String templateMessage = this.templateEngine.process("welcome-template", context);

	        MailMessage mail = MailMessage.builder()
	                .to(email)
	                .from("fuskinhatestes@gmail.com")
	                .message(templateMessage)
	                .subject("Aula do Experts Club - Spring Mail Sender")
	                .bodyFile("headerLogo", new ClassPathResource("static/images/Logo_ExpertsClub.png"))
	                .build();

	        sendAdvancedEmail(mail);
	    }
	 


	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 private void sendAdvancedEmail(MailMessage mail) {
	        log.info("Sending email.");

	        try {
	            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

	            helper.setFrom(mail.getFrom(), "");
	            helper.setTo(mail.getTo());
	            helper.setSubject(mail.getSubject());
	            helper.setText(mail.getMessage(), true);
	            for (Map.Entry<String, ClassPathResource> map : mail.getBodyFiles().entrySet()) {
	                helper.addInline(map.getKey(), map.getValue());
	            }

	            
	            this.javaMailSender.send(mimeMessage);
	            log.info("Advanced email sent successfully.");
	        } catch (Exception e) {
	            log.error("Error when tried to send the email.");
	        }
	    }















	@Override
	public void sendSimpleEmail(String email) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setFrom("kais.benhajamor@softeam.fr");
		sm.setTo(email);
		sm.setSubject("Formulaire d'empreinte carbone");
		sm.setText("");
		
		javaMailSender.send(sm);
		
	}


}
