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
		
		//until we run out of turns
		while(turns < Config.MAX_TURNS) {
			
			int countryIndex = 0;
			for(var country : countryPool.getCountries()) {
				
				//returns a tradeProposal if proposing a trade after turn
				var trade = country.takeTurn();
				
				//if there's a trade proposal...
				if(trade != null) {
					//update the clients about the trade proposal
					sendUpdate();
					
					//a trade proposal isn't makde to a specific country, cause the 'asker' doesn't really care
					//so we see if any country (other than the proposer) will accept the trade
					for(var targetCountry : countryPool.getCountries()) {
						if(targetCountry != country && targetCountry.contemplateTrade(trade)) {
							//we found a match yay
							trade.getTransfer().setRespondingCountry(targetCountry);
							
							//add some metadata you our countries. should probably extract this logic
							//but some spaghetti code kinda requires it be here. TODO revisit
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
							
							//execute the trade for both countries
							trade.execute(targetCountry);
							
							//stop look for trade partners
							break;
						}
					}
				}
				
				//update who's taking their turn
				country.setTakingTurn(false);
				countryPool.getCountries().get((countryIndex + 1) % countryPool.getCountries().size()).setTakingTurn(true);
				
				//update the clients
				sendUpdate();
				
				//should i just make this a for loop, idk
				countryIndex++;
			}
			
			turns++;
		}
	}

	//custom initialization
	//TODO I should stick this in a super class or something
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
