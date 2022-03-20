package com.countrysim.CountrySimulator.sim.countries;

import com.countrysim.CountrySimulator.sim.ai.Oracle;
import com.countrysim.CountrySimulator.sim.ai.OracleFactory;
import com.countrysim.CountrySimulator.sim.ai.OracleType;
import com.countrysim.CountrySimulator.sim.ai.Prophecy;
import com.countrysim.CountrySimulator.sim.resources.ResourcePool;
import com.countrysim.CountrySimulator.sim.utilities.Config;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Country {
	private String name;
	
	private Oracle oracle;
	private Prophecy prophecy;
	private ResourcePool resourcePool;
	private CountryPool countryPool;
	
	private long computeTime;
	
	public Country(String name) {
		this.name = name;
		resourcePool = new ResourcePool();
	}
	
	//create a copy, minus oracle/prophecy
	public Country(Country country) {
		name = country.getName();
		resourcePool = new ResourcePool(country.getResourcePool());
		countryPool = country.getCountryPool();
	}
	
	public void initialize() {
		oracle = OracleFactory.create(Config.ORACLE_TYPE, this);
		long start = System.nanoTime();
		prophecy = oracle.foresee(Config.SEARCH_DEPTH);
		long end = System.nanoTime();
		computeTime = end - start;
		System.out.println();
	}
	
	//getters and setters
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public Prophecy getProphecy() { return prophecy; }
	public ResourcePool getResourcePool() { return resourcePool; }
	
	@JsonIgnore
	public CountryPool getCountryPool() { return countryPool; }
	public void setCountryPool(CountryPool countryPool) { this.countryPool = countryPool; }
	
	public long getComputeTime() { return computeTime; }
}
