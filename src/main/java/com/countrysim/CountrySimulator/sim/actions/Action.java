package com.countrysim.CountrySimulator.sim.actions;

import com.countrysim.CountrySimulator.sim.countries.Country;

public abstract class Action {
	private String name;
	
	public abstract boolean isValid();
	public abstract boolean execute();
	public abstract Country tryExecute();
	
	//getters and setters
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
