package com.softeam.cfc.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.softeam.cfc.config.CacheEnumKey;
import com.softeam.cfc.domain.EmpreinteCarbone;
import com.softeam.cfc.dto.CarbonFootPrintFormDTO;
import com.softeam.cfc.dto.EmpreinteCarboneDto;
import com.softeam.cfc.dto.LocomotionDto;
import com.softeam.cfc.dto.enums.DeviceType;
import com.softeam.cfc.dto.enums.MoyenTransport;
import com.softeam.cfc.dto.enums.TypeVeloEnum;
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
	public EmpreinteCarboneDto calculateDailyCarbonFootprintForOffice(CarbonFootPrintFormDTO cfc) {
		
		EmpreinteCarboneDto e = new EmpreinteCarboneDto();
		 // Assurer que presenceDays n'est pas 0 pour éviter la division par zéro
		int presenceDays = Integer.valueOf(cfc.getPresenceDays());
        if (presenceDays <= 0) {
            throw new IllegalArgumentException("Le nombre de jours de présence doit être supérieur à 0");
        }
        double emissionFactor = getEmissionFactorValue(cfc);
			
        double laptopConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+DeviceType.LAPTOP.getValue()) ;
        double screenConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+DeviceType.MONITOR.getValue()) ;
        double phoneConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+DeviceType.PHONE.getValue()) ;
        double desktopConsumption = (double) cache.getIfPresent(CacheEnumKey.DEVICE_CONSUMPTION.getValue()+DeviceType.DESKTOP.getValue());
        
        double carbonFootPrint = emissionFactor + laptopConsumption + screenConsumption + phoneConsumption + desktopConsumption; 
        carbonFootPrint = truncateDouble(carbonFootPrint, 4);
        collaborateurCarbonFootPrintService.addColloboraeurCarbonFootPrint(cfc, String.valueOf(carbonFootPrint));
        
        e.setEmpreinteParJourDePresence(String.valueOf(carbonFootPrint));
        e.setEmpreinteTotalParSemaine(String.valueOf(carbonFootPrint * presenceDays));
        return e;
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
			      sb.append(UNDERSCORE).append("Yes".equalsIgnoreCase(locomotion.getCovoiturage()) ? "Oui" : "Non");
			}
		}
		
		if(MoyenTransport.MOTO.getValue().equalsIgnoreCase(locomotion.getModeTransport()))
		{
			sb.append(UNDERSCORE).append(locomotion.getTypeMoto());
		}
		
		if(MoyenTransport.VELO.getValue().equalsIgnoreCase(locomotion.getModeTransport()))
		{
			sb.append(UNDERSCORE).append("vae".equalsIgnoreCase(locomotion.getVae()) ?  TypeVeloEnum.ELECTRIC_ASSISTANCE.getValue() : TypeVeloEnum.STANDARD.getValue() );
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

	private double truncateDouble(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.DOWN);
        return bd.doubleValue();
    }

}
