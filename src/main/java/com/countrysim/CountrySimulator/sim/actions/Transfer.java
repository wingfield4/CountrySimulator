package com.countrysim.CountrySimulator.sim.actions;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.countries.TradeProposal;
import com.countrysim.CountrySimulator.sim.resources.ResourceFactory;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class Transfer extends Action {
	private static final double RECEIVING_AMOUNT_RATIO = .96;
	
	private Country initiatingCountry;
	private Country respondingCountry;
	private TransferType transferType;
	private ResourceType sendingResourceType;
	private int sendingAmount;
	private ResourceType receivingResourceType;
	private int receivingAmount;
	
	//only instantiate initiatingCountry - this is a theoretical transfer for planning
	public Transfer(Country initiatingCountry, TransferType transferType, ResourceType sendingResourceType, ResourceType receivingResourceType) {
		this.initiatingCountry = initiatingCountry;
		this.transferType = transferType;
		this.sendingResourceType = sendingResourceType;
		this.receivingResourceType = receivingResourceType;
		var sendingResource = ResourceFactory.create(sendingResourceType);
		var receivingResource = ResourceFactory.create(receivingResourceType);
		calculateAmounts();
		
		setId("Propose Transfer");
		setName("Propose Transfer");
		setDescription("Proposed trading (" + sendingAmount + ") " + sendingResource.getName() + " for (" + receivingAmount + ") " + receivingResource.getName());
	}
	
	public void setRespondingCountry(Country respondingCountry) {
		this.respondingCountry = respondingCountry;
	}
	
	private void calculateAmounts() {
		var sendingResource = ResourceFactory.create(sendingResourceType);
		var receivingResource = ResourceFactory.create(receivingResourceType);
		
		var available = initiatingCountry.getResourcePool().getResourceCount(sendingResourceType);
		
		switch(transferType) {
			case TransferSome:
				sendingAmount = (int)(available * .25);
				break;
			case TransferHalf:
				sendingAmount = (int)(available * .5);
				break;
			case TransferMost:
				sendingAmount = (int)(available * .75);
				break;
		}
		
		double resourceRatio = sendingResource.getWeight()/receivingResource.getWeight();
		receivingAmount = (int)(sendingAmount*resourceRatio*RECEIVING_AMOUNT_RATIO);
		this.setProbabilityOfSuccess(.999);
		
		//adjust receiving amount based on resources in world
		int highestSingleCountryAmount = 0;
		int worldAmount = 0;
		
		for(var country : initiatingCountry.getCountryPool().getCountries()) {
			if(country.getName() != initiatingCountry.getName()) {
				int countryAmount = country.getResourcePool().getValueMap().get(receivingResourceType);
				worldAmount += countryAmount;
				highestSingleCountryAmount = Math.max(countryAmount, highestSingleCountryAmount);
			}
		}

		if(worldAmount < receivingAmount) {
			this.setProbabilityOfSuccess(.1);
		} else if(highestSingleCountryAmount < receivingAmount) {
			receivingAmount = highestSingleCountryAmount; //adjust receiving amount
		}
	}
	
	public boolean isValid(Country respondingCountry) {
		var sendingResource = ResourceFactory.create(sendingResourceType);
		var receivingResource = ResourceFactory.create(receivingResourceType);
		
		if(this.getProbabilityOfSucces() <= 0)
			return false;
		
		if(sendingAmount == 0 || receivingAmount == 0)
			return false;
		
		//fails if sending resource is not tradeable
		if(!sendingResource.isTradeable())
			return false;

		//fails if receiving resource is not tradeable
		if(!receivingResource.isTradeable())
			return false;
		
		if(respondingCountry != null) {
			//fails if we don't actually have the resource
			if(respondingCountry.getResourcePool().getResourceCount(receivingResourceType) < receivingAmount) {
				System.out.println("hej");
				return false;
			}
		}
		
		//fails if we don't actually have the resource
		if(initiatingCountry.getResourcePool().getResourceCount(sendingResourceType) <= 0)
			return false;
		
		return true;
	}
	
	//for checking with a specific responding country
	public boolean isValid() {
		return isValid(respondingCountry);
	}
	
	//returns true if successfully executed
	//exxecuting a transfer just return a trade proposal
	public TradeProposal execute() { return execute(initiatingCountry); }
	private TradeProposal execute(Country initiatingCountry) {
		if(!isValid())
			return null;
		
		return new TradeProposal(initiatingCountry, this);
	}
	
	public void finalize(Country initiatingCountry, Country respondingCountry) {
		if(initiatingCountry != null) {
			initiatingCountry.getResourcePool().adjustResource(sendingResourceType, -sendingAmount);
			initiatingCountry.getResourcePool().adjustResource(receivingResourceType, receivingAmount);
		}
		
		if(respondingCountry != null) {
			respondingCountry.getResourcePool().adjustResource(sendingResourceType, sendingAmount);
			respondingCountry.getResourcePool().adjustResource(receivingResourceType, -receivingAmount);
		}
	}
	
	public Country tryExecute() {
		Country copy = new Country(initiatingCountry);
		finalize(copy, null);
		return copy;
	}
	
	public Country tryExecute(Country country) {
		Country copy = new Country(country);
		finalize(null, copy);
		return copy;
	}
	
	public Action clone(Country country) {
		return new Transfer(country, transferType, sendingResourceType, receivingResourceType);
	}
	
	public double getStateQualityDelta() {
		return 0;
	}
	
	public double getInitiatingFinalizedStateQualityDelta() {
		return tryExecute().getStateQuality() - initiatingCountry.getStateQuality();
	}
	
	public double getRespondingFinalizedStateQualityDelta() {
		return tryExecute(respondingCountry).getStateQuality() - respondingCountry.getStateQuality();
	}
	
	public String getFinalizedInitiatingDescription() {
		if(respondingCountry == null)
			return null;
		
		var sendingResource = ResourceFactory.create(sendingResourceType);
		var receivingResource = ResourceFactory.create(receivingResourceType);
		return "Finalized trading (" + sendingAmount + ") " + sendingResource.getName() + " for (" + receivingAmount + ") " + receivingResource.getName()
			 + " with " + respondingCountry.getName();
	}
	
	public String getFinalizedRespondingDescription() {
		if(initiatingCountry == null)
			return null;
		
		var sendingResource = ResourceFactory.create(sendingResourceType);
		var receivingResource = ResourceFactory.create(receivingResourceType);
		return "Agreed to trade (" + receivingAmount + ") " + receivingResource.getName() + " for (" + sendingAmount + ") " + sendingResource.getName()
			 + " with " + initiatingCountry.getName();
	}
}
