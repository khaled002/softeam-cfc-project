package com.softeam.cfc.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.softeam.cfc.domain.DeviceConsumption;
import com.softeam.cfc.domain.EmissionFactor;
import com.softeam.cfc.domain.HeatingEmission;
import com.softeam.cfc.repository.DeviceConsumptionRepository;
import com.softeam.cfc.repository.EmissionFactorRepository;
import com.softeam.cfc.repository.HeatingEmissionRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader {

	@Autowired
    private Cache<String, Object> caffeineCache;

    @Autowired
    private EmissionFactorRepository emissionFactorRepository;

    @Autowired
    private DeviceConsumptionRepository deviceConsumptionRepository;

    @Autowired
    private HeatingEmissionRepository heatingEmissionRepository;

    @PostConstruct
    public void loadDataIntoCache() {
        // Charger et mettre en cache les facteurs d'émission
    	List<EmissionFactor> emissionFactors = emissionFactorRepository.findAll();
    	for (EmissionFactor factor : emissionFactors) {
    	    // Créer une clé unique pour chaque combinaison
    	    String key = CacheEnumKey.EMISSION_FACTOR.getValue() +
    	                 factor.getType() +
    	                 (StringUtils.isNotBlank(factor.getSubType()) ? "_" +factor.getSubType() : "")  +
    	                 (StringUtils.isNotBlank(factor.getEnergy()) ?  "_" +factor.getEnergy()  : "")  +
    	                 (StringUtils.isNotBlank(factor.getCarpooling()) ? "_" + factor.getCarpooling() : "");

    	    // Mettre en cache le facteur d'émission en utilisant la clé unique
    	    caffeineCache.put(key, factor.getFactor());
    	}

        // Charger et mettre en cache la consommation des appareils
        List<DeviceConsumption> deviceConsumptions = deviceConsumptionRepository.findAll();
        for (DeviceConsumption consumption : deviceConsumptions) {
            caffeineCache.put(CacheEnumKey.DEVICE_CONSUMPTION.getValue() + consumption.getDeviceType(), consumption.getConsumptionPerHour());
        }

        // Charger et mettre en cache les émissions de chauffage
        List<HeatingEmission> heatingEmissions = heatingEmissionRepository.findAll();
        for (HeatingEmission emission : heatingEmissions) {
            caffeineCache.put(CacheEnumKey.HEATING_EMISSION.getValue() + emission.getHeatingType(), emission.getEmissionPerDay());
        }

        
        log.info("Data loaded into cache successfully!");
    }
}
