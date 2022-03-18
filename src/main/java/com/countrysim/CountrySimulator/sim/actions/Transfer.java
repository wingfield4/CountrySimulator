package com.countrysim.CountrySimulator.sim.actions;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceFactory;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class Transfer extends Action {
	private static final double RECEIVING_AMOUNT_RATIO = .9;
	
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
		
		setName("Transfer");
		setDescription("Trade (" + sendingAmount + ") " + sendingResource.getName() + " for (" + receivingAmount + ") " + receivingResource.getName());
		
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
	}
	
	public boolean isValid() {
		var sendingResource = ResourceFactory.create(sendingResourceType);
		var receivingResource = ResourceFactory.create(receivingResourceType);
		
		if(sendingAmount == 0 || receivingAmount == 0)
			return false;
		
		//fails if sending resource is not tradeable
		if(!sendingResource.isTradeable())
			return false;

		//fails if receiving resource is not tradeable
		if(!receivingResource.isTradeable())
			return false;
		
		if(respondingCountry != null) {
			//TODO check stuff here
		}
		
		//fails if we don't actually have the resource
		if(initiatingCountry.getResourcePool().getResourceCount(sendingResourceType) <= 0)
			return false;
		
		return true;
	}
	
	//returns true if successfully executed
	public boolean execute() { return execute(initiatingCountry); }
	private boolean execute(Country initiatingCountry) {
		if(!isValid())
			return false;
		
		initiatingCountry.getResourcePool().adjustResource(sendingResourceType, -sendingAmount);
		initiatingCountry.getResourcePool().adjustResource(receivingResourceType, receivingAmount);
		
		if(respondingCountry != null) {
			respondingCountry.getResourcePool().adjustResource(sendingResourceType, sendingAmount);
			respondingCountry.getResourcePool().adjustResource(receivingResourceType, -receivingAmount);
		}
		
		return true;
	}
	
	public Country tryExecute() {
		Country copy = new Country(initiatingCountry);
		execute(copy);
		return copy;
	}
}
