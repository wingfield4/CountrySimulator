package com.countrysim.CountrySimulator.sim.resources;

public class Housing extends PopulationDependentResource {
	public Housing() {
		setResourceType(ResourceType.Housing);
		setName("Housing");
		setWeight(15);
		setTradeable(false);
	}
}
