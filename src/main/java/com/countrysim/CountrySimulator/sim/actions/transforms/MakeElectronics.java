package com.countrysim.CountrySimulator.sim.actions.transforms;

import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class MakeElectronics extends Transform {
	public MakeElectronics(Country country) {
		super(country);
		setName("Make Electronics");
		setDescription("Turn Metallic Alloys and Metallic Elements into Electronics");
		
		//requirements
		this.addResourceRequirement(ResourceType.MetallicAlloys, 2);
		this.addResourceRequirement(ResourceType.MetallicElements, 3);
		this.addResourceRequirement(ResourceType.Population, 1);
		
		//deltas
		this.addResourceDelta(ResourceType.Electronics, 2);
		this.addResourceDelta(ResourceType.ElectronicsWaste, 1);
		this.addResourceDelta(ResourceType.MetallicAlloys, -2);
		this.addResourceDelta(ResourceType.MetallicElements, -3);
	}
}
