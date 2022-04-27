package com.countrysim.CountrySimulator.sim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.countrysim.CountrySimulator.sim.countries.ActionSummary;
import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.countries.CountryPool;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;
import com.countrysim.CountrySimulator.sim.utilities.Config;
import com.countrysim.CountrySimulator.sim.utilities.GenerationMethod;
import com.countrysim.CountrySimulator.web.TownCrier;
import com.countrysim.CountrySimulator.web.response.WorldStateResponse;

@Component
public class GameMaster {
	private TownCrier townCrier;
	private CountryPool countryPool = new CountryPool();
	
	public GameMaster(TownCrier townCrier) {
		this.townCrier = townCrier;
	}
	
	public void initialize() {
		if(Config.GENERATION_METHOD == GenerationMethod.CSV && Config.CONFIG_FILE_NAME != null)
			initializeWithCSV();
		else
			initializeWithGeneration();
		
		startGame();
	}
	
	private void sendUpdate() {
		var worldStateResponse = new WorldStateResponse()
				.setCountryPool(countryPool);
		
		townCrier.sendMessage(worldStateResponse);
	}
	
	//game loop
	private void startGame() {
		int turns = 0;
		while(turns < Config.MAX_TURNS) {
			
			int countryIndex = 0;
			for(var country : countryPool.getCountries()) {
				var trade = country.takeTurn();
				
				if(trade != null) {
					for(var targetCountry : countryPool.getCountries()) {
						if(targetCountry != country && targetCountry.contemplateTrade(trade)) {
							trade.getTransfer().setRespondingCountry(targetCountry);
							country.addActionSummary(
									new ActionSummary(
											"Transfer",
											trade.getTransfer().getName(), 
											trade.getTransfer().getFinalizedInitiatingDescription(), 
											trade.getTransfer().getInitiatingFinalizedStateQualityDelta()));
							targetCountry.addActionSummary(
									new ActionSummary(
											"Transfer",
											trade.getTransfer().getName(), 
											trade.getTransfer().getFinalizedRespondingDescription(), 
											trade.getTransfer().getRespondingFinalizedStateQualityDelta()));
							trade.execute(targetCountry);
							sendUpdate();
							break;
						}
					}
				}
				
				country.setTakingTurn(false);
				countryPool.getCountries().get((countryIndex + 1) % countryPool.getCountries().size()).setTakingTurn(true);
				
				sendUpdate();
				
				countryIndex++;
			}
			
			turns++;
		}
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
				for(int i = 0; i < numberOfCountries; i++) {
					countryPool.getCountries()
						.get(i)
						.getResourcePool()
						.setResource(resourceType, Config.RESOURCE_DISTRIBUTIONS.get(resourceType).getInt());
				}
			});
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
	}
	
	//getters and setters
	public CountryPool getCountryPool() { return countryPool; }
}
