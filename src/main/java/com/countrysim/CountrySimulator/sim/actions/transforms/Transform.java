package com.countrysim.CountrySimulator.sim.actions.transforms;

import java.util.HashMap;
import java.util.Map;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public abstract class Transform extends Action {
	private Country country;
	private Map<ResourceType, Integer> resourceRequirements = new HashMap<ResourceType, Integer>();
	private Map<ResourceType, Integer> resourceDeltas = new HashMap<ResourceType, Integer>();
	
	public Transform(Country country) {
		this.country = country;
	}
	
	protected void addResourceDelta(ResourceType resourceType, Integer value) {
		resourceDeltas.put(resourceType, value);
	}
	
	protected void addResourceRequirement(ResourceType resourceType, Integer value) {
		resourceRequirements.put(resourceType, value);
	}
	
	public boolean isValid() { return isValid(country); };
	private boolean isValid(Country country) {
		//for each requirement, if requirement is >= 0, the country must have that resource and 
		//its resource value must be greater than or equal to the requirement value
		return resourceRequirements.entrySet()
				.stream()
				.allMatch(requirement -> requirement.getValue() <= 0
						|| (country.getResourcePool().getValueMap().get(requirement.getKey()) != null
						&& country.getResourcePool().getValueMap().get(requirement.getKey()) >= requirement.getValue()));
	}

	//returns true if successfully executed
	public boolean execute() { return execute(country); }
	private boolean execute(Country country) {
		if(!isValid())
			return false;
		
		resourceDeltas.entrySet()
			.stream() //for each delta, adjust that resource in the country's resource pool
			.forEach(entry -> country.getResourcePool().adjustResource(entry.getKey(), entry.getValue()));
		
		return true;
	}
	
	//execute, but return a new Country reflecting changes rather than administering changes to the main country
	//this is useful for our searching and planning
	public Country tryExecute() {
		Country copy = new Country(country);
		execute(copy);
		return copy;
	}
}
