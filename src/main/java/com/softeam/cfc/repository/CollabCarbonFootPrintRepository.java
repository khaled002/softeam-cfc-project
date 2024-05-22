package com.softeam.cfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.CollabCarbonFootPrint;

@Repository
public interface CollabCarbonFootPrintRepository extends JpaRepository<CollabCarbonFootPrint, Long> {

	@Query(value = "SELECT * FROM collab_carbon_footprint  where collaborateur_id=:id", nativeQuery = true)
	CollabCarbonFootPrint findByCollaborateurId(@Param(value = "id") Long id);
}
