package com.softeam.cfc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.softeam.cfc.config.CacheEnumKey;
import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.dto.LocomotionDto;
import com.softeam.cfc.dto.enums.MoyenTransport;
import com.softeam.cfc.service.CollaborateurCarbonFootPrintService;
import com.softeam.cfc.service.EmpreinteCarboneService;

import io.micrometer.common.util.StringUtils;

@Service
public class EmpreinteCarboneServiceImpl implements EmpreinteCarboneService {

	private static final String UNDERSCORE = "_";
	@Autowired
	private Cache<String, Object> cache;
	
	@Autowired
	private CollaborateurCarbonFootPrintService collaborateurCarbonFootPrintService;
	
	@Override
	public double calculateDailyCarbonFootprintForOffice(CarbonFootPrintFormDTO cfc) {
		 // Assurer que presenceDays n'est pas 0 pour éviter la division par zéro
		int presenceDays = Integer.valueOf(cfc.getPresenceDays());
        if (presenceDays <= 0) {
            throw new IllegalArgumentException("Le nombre de jours de présence doit être supérieur à 0");
        }
        double emissionFactor = getEmissionFactorValue(cfc);
			
        double laptopConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getLaptop()) * presenceDays;
        double screenConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getMonitor()) * presenceDays;
        double phoneConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getPhone()) * presenceDays;
        double desktopConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+cfc.getDesktop()) * presenceDays;
        
        double carbonFootPrint = emissionFactor + laptopConsumption + screenConsumption + phoneConsumption + desktopConsumption; 
        
        collaborateurCarbonFootPrintService.addColloboraeurCarbonFootPrint(cfc, String.valueOf(carbonFootPrint));
        
        return carbonFootPrint;
	}

	private String getEmissionFactorKey(LocomotionDto locomotion)
	{
		StringBuilder sb = new StringBuilder(CacheEnumKey.EMISSION_FACTOR.getValue());
		sb.append(locomotion.getModeTransport());
		
		if(MoyenTransport.VOITURE.getValue().equalsIgnoreCase(locomotion.getModeTransport()))
		{
			if(StringUtils.isNotBlank(locomotion.getGabarit()))
				sb.append(UNDERSCORE).append(locomotion.getGabarit());
			
			if(StringUtils.isNotBlank(locomotion.getTypeEnergie()))
				sb.append(UNDERSCORE).append(locomotion.getTypeEnergie());
			
			if(StringUtils.isNotBlank(locomotion.getCovoiturage()))
			{
			     boolean b = Boolean.valueOf(locomotion.getCovoiturage());
			     sb.append(UNDERSCORE).append(b ? "Oui" : "Non");
			}
		}
		
		return sb.toString();
	}
	
	
	private double getEmissionFactorValue(CarbonFootPrintFormDTO cfc)
	{	// retourne la somme des cfc trajets aller et retour 
		return cfc.getLocomotions()
                .stream()
                .mapToDouble(l -> {
                    double distance = Double.parseDouble(l.getDistance());
                    double emissionFactorPerKm = (double) cache.getIfPresent(getEmissionFactorKey(l));
                    return distance * emissionFactorPerKm * 2;
                })
                .sum();
	}
}
