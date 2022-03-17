package com.countrysim.CountrySimulator.web.response;

import com.countrysim.CountrySimulator.sim.countries.CountryPool;

public class WorldStateResponse {
	private CountryPool countryPool;
	
	//getters and setters
	public CountryPool getCountryPool() { return countryPool; }
	public WorldStateResponse setCountryPool(CountryPool countryPool) { this.countryPool = countryPool; return this; }
}
