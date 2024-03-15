package com.softeam.cfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.HeatingEmission;

@Repository
public interface HeatingEmissionRepository extends JpaRepository<HeatingEmission, String> {}
