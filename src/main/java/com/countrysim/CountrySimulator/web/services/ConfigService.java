package com.countrysim.CountrySimulator.web.services;

import com.countrysim.CountrySimulator.sim.resources.ResourceType;
import com.countrysim.CountrySimulator.sim.utilities.Config;
import com.countrysim.CountrySimulator.sim.utilities.GenerationMethod;
import com.countrysim.CountrySimulator.sim.utilities.distributions.NormalDistribution;
import com.countrysim.CountrySimulator.web.request.RunSimulationRequest;

public class ConfigService {
	public static void setConfig(RunSimulationRequest request) {
		switch(request.getGenerationMethod()) {
			case "generated":
				Config.GENERATION_METHOD = GenerationMethod.Generated;
				break;
			case "csv":
				Config.GENERATION_METHOD = GenerationMethod.CSV;
				break;
		}

		Config.CONFIG_FILE_NAME = request.getConfigFileName();
		Config.NUMBER_OF_COUNTRIES = request.getNumberOfCountries();
		Config.SEARCH_DEPTH = request.getSearchDepth();
		Config.ORACLE_TYPE = Config.ORACLE_NAME_MAP.get(request.getOracle());
		
		Config.DISCOUNT_GAMMA = request.getDiscountGamma();
		Config.DISCOUNT_OFFSET = request.getDiscountOffset();
		Config.PROBABILITY_PENALTY = request.getProbabilityPenalty();
		
		//adjust resource distributions
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.Electronics, new NormalDistribution(request.getElectronicsRange()[0], request.getElectronicsRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.ElectronicsWaste, new NormalDistribution(request.getElectronicsWasteRange()[0], request.getElectronicsWasteRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.Housing, new NormalDistribution(request.getHousingRange()[0], request.getHousingRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.HousingWaste, new NormalDistribution(request.getHousingWasteRange()[0], request.getHousingWasteRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.MetallicAlloys, new NormalDistribution(request.getMetallicAlloysRange()[0], request.getMetallicAlloysRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.MetallicAlloysWaste, new NormalDistribution(request.getMetallicAlloysWasteRange()[0], request.getMetallicAlloysWasteRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.MetallicElements, new NormalDistribution(request.getMetallicElementsRange()[0], request.getMetallicElementsRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.Population, new NormalDistribution(request.getPopulationRange()[0], request.getPopulationRange()[1]));
		Config.RESOURCE_DISTRIBUTIONS
			.replace(ResourceType.Timber, new NormalDistribution(request.getTimberRange()[0], request.getTimberRange()[1]));
	}
}
