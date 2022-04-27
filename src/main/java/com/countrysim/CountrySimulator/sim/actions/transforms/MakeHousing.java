package com.countrysim.CountrySimulator.sim.actions.transforms;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class MakeHousing extends Transform {
	public MakeHousing(Country country, int multiplier) {
		super(country, multiplier);
		setId("Make Housing");
		setName("Make Housing");
		setDescription("Make (" + multiplier + ") Housing");
		
		//requirements
		this.addResourceRequirement(ResourceType.MetallicAlloys, 3);
		this.addResourceRequirement(ResourceType.MetallicElements, 1);
		this.addResourceRequirement(ResourceType.Population, 5);
		this.addResourceRequirement(ResourceType.Timber, 5);
		
		//deltas
		this.addResourceDelta(ResourceType.Housing, 1);
		this.addResourceDelta(ResourceType.HousingWaste, 1);
		this.addResourceDelta(ResourceType.MetallicAlloys, -3);
		this.addResourceDelta(ResourceType.MetallicElements, -1);
		this.addResourceDelta(ResourceType.Timber, -5);
	}
	
	public Action clone(Country country) {
		return new MakeHousing(country, multiplier);
	}
}
