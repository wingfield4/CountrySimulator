package com.countrysim.CountrySimulator.sim.countries;

import java.util.ArrayList;
import java.util.List;

public class CountryPool {
	private List<Country> countries = new ArrayList<Country>();
	
	public void addCountry(Country country) {
		countries.add(country);
	}
	
	//getters and setters
	public List<Country> getCountries() { return countries; }
}
