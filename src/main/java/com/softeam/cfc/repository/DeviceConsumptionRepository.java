package com.softeam.cfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softeam.cfc.domain.DeviceConsumption;

@Repository
public interface DeviceConsumptionRepository extends JpaRepository<DeviceConsumption, String> {}

