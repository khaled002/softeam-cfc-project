package com.softeam.cfc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.Collaborateur;


@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
	
	
	 @Query(value = "SELECT * FROM collaborateur  where email=:email", nativeQuery = true )
	 public Optional<Collaborateur> findByEmail(@Param("email") String email);
	 
}
