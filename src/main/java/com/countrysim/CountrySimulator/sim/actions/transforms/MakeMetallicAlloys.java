package com.countrysim.CountrySimulator.sim.actions.transforms;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class MakeMetallicAlloys extends Transform {
	public MakeMetallicAlloys(Country country, int multiplier) {
		super(country, multiplier);
		setId("Make Metallic Alloys");
		setName("Make (" + multiplier + ") Metallic Alloys");
		setDescription("Turn Metallic Elements into Metallic Alloys");
		
		//requirements
		this.addResourceRequirement(ResourceType.MetallicElements, 2);
		this.addResourceRequirement(ResourceType.Population, 1);
		
		//deltas
		this.addResourceDelta(ResourceType.MetallicAlloys, 1);
		this.addResourceDelta(ResourceType.MetallicElements, -2);
	}
}
