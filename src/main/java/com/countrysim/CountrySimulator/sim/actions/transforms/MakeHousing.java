package com.countrysim.CountrySimulator.sim.actions.transforms;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class MakeHousing extends Transform {
	public MakeHousing(Country country, int multiplier) {
		super(country, multiplier);
		setId("Make Housing");
		setName("Make (" + multiplier + ") Housing");
		setDescription("Turn Metallic Alloys, Metallic Elements, and Timber into Housing");
		
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
}
