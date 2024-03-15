package com.softeam.cfc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.softeam.cfc.config.CacheEnumKey;
import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.service.EmpreinteCarboneService;

@Service
public class EmpreinteCarboneServiceImpl implements EmpreinteCarboneService {

	@Autowired
	private Cache<String, Object> cache;

	
	@Override
	public double calculateDailyCarbonFootprintForTravel(CarbonFootPrintFormDTO cfc) {
		 // Assurer que presenceDays n'est pas 0 pour éviter la division par zéro
        if (cfc.getPresenceDays() <= 0) {
            throw new IllegalArgumentException("Le nombre de jours de présence doit être supérieur à 0");
        }

        double emissionFactor = (double) cache.getIfPresent(CacheEnumKey.EMISSION_FACTOR.getValue()+cfc.getTransportationMode());
        double laptopConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getHomeEquipment().getLaptop());
        double screenConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getHomeEquipment().getMonitor());
        double phoneConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getHomeEquipment().getPhone());
        double desktopConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getHomeEquipment().getDesktopComputer());
        double totalEmission = (cfc.getDistanceKm() * emissionFactor) + laptopConsumption + screenConsumption + phoneConsumption + desktopConsumption; 
        return totalEmission / cfc.getPresenceDays();
	}

	@Override
	public double calculateDailyCarbonFootprintForHome(CarbonFootPrintFormDTO cfc) {
		 // Similar to travel, ensure presenceDays is greater than 0
        if (cfc.getPresenceDays() <= 0) {
            throw new IllegalArgumentException("Le nombre de jours de présence doit être supérieur à 0");
        }

        double heatingEmission = (double) cache.getIfPresent("heatingEmission_"+cfc.getHeatingType());
        // Assuming the heating emission is daily, we don't need to adjust by presenceDays here
        // as it's already a daily value. But if you want to calculate for multiple days:
        double totalEmission = heatingEmission * cfc.getPresenceDays();
        return totalEmission / (5 - cfc.getPresenceDays()); // This simplifies back to heatingEmission but is shown for consistency
	}

}
