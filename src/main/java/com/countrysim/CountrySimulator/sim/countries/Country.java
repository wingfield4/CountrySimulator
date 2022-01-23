package com.countrysim.CountrySimulator.sim.countries;

import com.countrysim.CountrySimulator.sim.ai.Oracle;
import com.countrysim.CountrySimulator.sim.ai.Prophecy;
import com.countrysim.CountrySimulator.sim.resources.ResourcePool;

public class Country {
	private String name;
	
	private Oracle oracle;
	private Prophecy prophecy;
	private ResourcePool resourcePool;
	
	public Country(String name) {
		this.name = name;
		resourcePool = new ResourcePool();
	}
	
	public Country(Country country) {
		name = country.getName();
		resourcePool = new ResourcePool(country.getResourcePool());
	}
	
	public void initialize() {
		oracle = new Oracle(this);
		prophecy = oracle.foresee(10);
	}
	
	//getters and setters
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public Oracle getOracle() { return oracle; }
	public Prophecy getProphecy() { return prophecy; }
	
	public ResourcePool getResourcePool() { return resourcePool; }
}
