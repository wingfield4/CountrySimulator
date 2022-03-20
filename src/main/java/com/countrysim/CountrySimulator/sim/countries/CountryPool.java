package com.countrysim.CountrySimulator.sim.countries;

import java.util.ArrayList;
import java.util.List;

import com.countrysim.CountrySimulator.sim.resources.ResourcePool;

public class CountryPool {
	private List<Country> countries = new ArrayList<Country>();
	
	public void addCountry(Country country) {
		country.setCountryPool(this);
		countries.add(country);
	}
	
	//getters and setters
	public List<Country> getCountries() { return countries; }
}
