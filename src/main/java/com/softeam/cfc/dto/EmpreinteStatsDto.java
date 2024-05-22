package com.softeam.cfc.dto;

import java.util.List;

import lombok.Data;

@Data
public class EmpreinteStatsDto {

	private String nbCollaborateursWhoAnswered;
	private String nbCollaborateursWhoDidNotanswer;
	private List<CollaborateurStatsDTO> collabStatsAnswered;
	private List<CollaborateurStatsDTO> collabStatsDidNotAnswer;
	

}
