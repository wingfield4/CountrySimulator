package com.countrysim.CountrySimulator.sim.actions;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.countries.TradeProposal;
import com.countrysim.CountrySimulator.sim.resources.ResourcePool;

public abstract class Action {
	private String id;
	private String name;
	private String description;
	private double probabilityOfSuccess = 1.0;
	
	public abstract boolean isValid();
	public abstract TradeProposal execute();
	public abstract Country tryExecute();
	public abstract Action clone(Country country);
	public abstract double getStateQualityDelta();
	
	//getters and setters
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public double getProbabilityOfSucces() { return probabilityOfSuccess; }
	public void setProbabilityOfSuccess(double probabilityOfSuccess) { this.probabilityOfSuccess = probabilityOfSuccess; }
	
	public ResourcePool getResultResourcePool() { return tryExecute().getResourcePool(); }
}
