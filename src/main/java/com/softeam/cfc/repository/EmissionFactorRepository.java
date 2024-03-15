package com.softeam.cfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.EmissionFactor;

@Repository
public interface EmissionFactorRepository extends JpaRepository<EmissionFactor, String> {}
