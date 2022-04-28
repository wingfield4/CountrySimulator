package com.countrysim.CountrySimulator.sim.actions.transforms;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class MakeFarmLand extends Transform {
	public MakeFarmLand(Country country, int multiplier) {
		super(country, multiplier);
		setId("Make Farm Land");
		setName("Make Farm Land");
		setDescription("Make (" + multiplier + ") Farm Land");
		
		//requirements
		this.addResourceRequirement(ResourceType.AvailableLand, 1);
		this.addResourceRequirement(ResourceType.MetallicAlloys, 2);
		this.addResourceRequirement(ResourceType.Timber, 5);
		this.addResourceRequirement(ResourceType.Population, 5);
		
		//deltas
		this.addResourceDelta(ResourceType.AvailableLand, -1);
		this.addResourceDelta(ResourceType.MetallicAlloys, -2);
		this.addResourceDelta(ResourceType.Timber, -5);
		this.addResourceDelta(ResourceType.FarmLand, 1);
	}
	
	public Action clone(Country country) {
		return new MakeFarmLand(country, multiplier);
	}
}
