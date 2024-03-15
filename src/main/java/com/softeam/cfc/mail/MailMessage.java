package com.softeam.cfc.mail;

import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailMessage {

	private String to;
	private String from;
	private String subject;
	private String message;
		
	@Singular
	private Map<String, ClassPathResource> bodyFiles;

}
