package com.softeam.cfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.EmpreinteCarbone;

@Repository
public interface EmpreinteCarboneRepository extends JpaRepository<EmpreinteCarbone, Long> {}
