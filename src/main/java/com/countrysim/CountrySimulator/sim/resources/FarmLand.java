package com.countrysim.CountrySimulator.sim.resources;

public class FarmLand extends PopulationDependentResource {
	public FarmLand() {
		setResourceType(ResourceType.FarmLand);
		setName("Farm Land");
		setWeight(15);
		setTradeable(false);
	}
}
