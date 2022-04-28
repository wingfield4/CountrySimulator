package com.countrysim.CountrySimulator.sim.resources;

public class AvailableLand extends Resource {
	public AvailableLand() {
		setResourceType(ResourceType.AvailableLand);
		setName("Available Land");
		setWeight(5);
		setTradeable(false);
	}
}
