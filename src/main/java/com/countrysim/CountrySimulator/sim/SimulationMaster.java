package com.countrysim.CountrySimulator.sim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.countries.CountryPool;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;
import com.countrysim.CountrySimulator.sim.utilities.Config;
import com.countrysim.CountrySimulator.sim.utilities.GenerationMethod;

@Component
public class SimulationMaster {
	CountryPool countryPool = new CountryPool();
	
	public void initialize() {
		if(Config.GENERATION_METHOD == GenerationMethod.CSV && Config.CONFIG_FILE_NAME != null)
			initializeWithCSV();
		else
			initializeWithGeneration();
	}

	//custom initialization
	private void initializeWithGeneration() {
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
//				int totalResourceCount = Config.RESOURCE_DISTRIBUTIONS.get(resourceType).getInt() * numberOfCountries;
//				var distributedResources = Utilities.distributeResources(numberOfCountries, totalResourceCount);
//				
//				for(int i = 0; i < numberOfCountries; i++) {
//					countryPool.getCountries().get(i).getResourcePool().setResource(resourceType, distributedResources.get(i));
//				}
				
				for(int i = 0; i < numberOfCountries; i++) {
					countryPool.getCountries()
						.get(i)
						.getResourcePool()
						.setResource(resourceType, Config.RESOURCE_DISTRIBUTIONS.get(resourceType).getInt());
				}
			});
		
		//initialize AIs
		countryPool.getCountries().forEach(country -> country.planAhead());
	}
	
	//initialize from csv
	//TODO error handle bad formats better
	private void initializeWithCSV() {
		String BASE_PATH = "/Users/david/projects/CountrySimulator/src/main/resources/static/config/";
		String DELIMITER = ",";
		
		try (BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + Config.CONFIG_FILE_NAME))) {
			String[] header = br.readLine().split(DELIMITER);
			
		    String line;
		    int rowIndex = 0;
		    while ((line = br.readLine()) != null) {
		        String[] row = line.split(DELIMITER);
		        
		        for(int colIndex = 0; colIndex < row.length; colIndex++) {
		        	if(colIndex == 0) {
		        		countryPool.addCountry(new Country(row[colIndex]));
		        	} else {
		        		countryPool.getCountries()
							.get(rowIndex)
							.getResourcePool()
							.setResource(Config.RESOURCE_NAME_MAP.get(header[colIndex]), Integer.parseInt(row[colIndex]));
		        	}
		        }
		        rowIndex++;
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//initialize AIs
		countryPool.getCountries().forEach(country -> country.planAhead());
	}
	
	//getters and setters
	public CountryPool getCountryPool() { return countryPool; }
}
