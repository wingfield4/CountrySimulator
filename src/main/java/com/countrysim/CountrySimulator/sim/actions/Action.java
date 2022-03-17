package com.countrysim.CountrySimulator.sim.actions;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourcePool;

public abstract class Action {
	private String name;
	private String description;
	
	public abstract boolean isValid();
	public abstract boolean execute();
	public abstract Country tryExecute();
	
	//getters and setters
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public ResourcePool getResultResourcePool() { return tryExecute().getResourcePool(); }
}
