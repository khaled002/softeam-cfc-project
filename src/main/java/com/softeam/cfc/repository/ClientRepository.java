package com.softeam.cfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {}

