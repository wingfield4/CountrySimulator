package com.countrysim.CountrySimulator.sim;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.countries.CountryPool;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;
import com.countrysim.CountrySimulator.sim.utilities.Config;
import com.countrysim.CountrySimulator.sim.utilities.Utilities;

@Component
public class GameMaster {
	CountryPool countryPool = new CountryPool();

	//custom initialization
	public void initialize() {
		int numberOfCountries = Config.NUMBER_OF_COUNTRIES;
		
		//set up the countries
		IntStream.range(0, numberOfCountries)
			.forEach(index -> {
				String countryName = Config.COUNTRY_NAMES.get(index);
				countryPool.addCountry(new Country(countryName));
			});
		
		//set up their resource pools
		Stream.of(ResourceType.values())
			.forEach(resourceType -> {
				int totalResourceCount = Config.RESOURCE_DISTRIBUTIONS.get(resourceType).getInt() * numberOfCountries;
				var distributedResources = Utilities.distributeResources(numberOfCountries, totalResourceCount);
				
				for(int i = 0; i < numberOfCountries; i++) {
					countryPool.getCountries().get(i).getResourcePool().setResource(resourceType, distributedResources.get(i));
				}
			});
		
		//initialize AIs
		countryPool.getCountries().forEach(country -> country.initialize());
	}
	
	//csv initialization
	
	//getters and setters
	public CountryPool getCountryPool() { return countryPool; }
}
