package com.softeam.cfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.CollabCarbonFootPrint;


@Repository
public interface CollabCarbonFootPrintRepository extends JpaRepository<CollabCarbonFootPrint, Long> {}
