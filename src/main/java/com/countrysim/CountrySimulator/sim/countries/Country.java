package com.countrysim.CountrySimulator.sim.countries;

import com.countrysim.CountrySimulator.sim.ai.Oracle;
import com.countrysim.CountrySimulator.sim.ai.OracleFactory;
import com.countrysim.CountrySimulator.sim.ai.OracleType;
import com.countrysim.CountrySimulator.sim.ai.Prophecy;
import com.countrysim.CountrySimulator.sim.resources.ResourcePool;
import com.countrysim.CountrySimulator.sim.utilities.Config;

public class Country {
	private String name;
	
	private Oracle oracle;
	private Prophecy prophecy;
	private ResourcePool resourcePool;
	
	public Country(String name) {
		this.name = name;
		resourcePool = new ResourcePool();
	}
	
	//create a copy, minus oracle/prophecy
	public Country(Country country) {
		name = country.getName();
		resourcePool = new ResourcePool(country.getResourcePool());
	}
	
	public void initialize() {
		oracle = OracleFactory.create(Config.ORACLE_TYPE, this);
		prophecy = oracle.foresee(Config.SEARCH_DEPTH);
		System.out.println();
	}
	
	//getters and setters
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	//public Oracle getOracle() { return oracle; }
	public Prophecy getProphecy() { return prophecy; }
	
	public ResourcePool getResourcePool() { return resourcePool; }
}
