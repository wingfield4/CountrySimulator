package com.countrysim.CountrySimulator.sim.resources;

public class Population extends Resource {
	public Population() {
		setResourceType(ResourceType.Population);
		setName("Population");
		setWeight(5);
		setTradeable(false);
	}
	
	//override for some custom function TODO
	public double getStateQuality(ResourcePool resourcePool) {
		return resourcePool.getValueMap()
				.get(ResourceType.Population);
	}
}
