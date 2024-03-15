package com.softeam.cfc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.softeam.cfc.domain.Collaborateur;
import com.softeam.cfc.dto.CollaborateurDTO;

@Mapper(componentModel = "spring")
public interface CollaborateurMapper {

    @Mapping(source = "uniteAffaire", target = "uniteCommerciale")
    CollaborateurDTO toDTO(Collaborateur collaborateur);
    
    @Mapping(source = "uniteCommerciale", target = "uniteAffaire" )
    Collaborateur toEntity(CollaborateurDTO collaborateur);

    List<CollaborateurDTO> toDTOs(List<Collaborateur> collaborateurs);
}

