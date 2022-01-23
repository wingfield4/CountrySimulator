package com.countrysim.CountrySimulator.sim.resources;

public class Population extends Resource {
	public Population() {
		setResourceType(ResourceType.Population);
		setName("Population");
	}
	
	//override for some custom function TODO
	public double getStateQuality(ResourcePool resourcePool) {
		return resourcePool.getValueMap()
				.get(ResourceType.Population);
	}
}
