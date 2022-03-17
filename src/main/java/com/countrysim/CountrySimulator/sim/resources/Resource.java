package com.countrysim.CountrySimulator.sim.resources;

public abstract class Resource {
	private ResourceType resourceType;
	private String name;
	private double weight = 1;
	private boolean tradeable = true;
	
	//default resource value
	//typically for tradeable 
	//more specialized resource (housing/population, etc..) should override this function
	public double getStateQuality(ResourcePool resourcePool) {
		return resourcePool.getValueMap().get(resourceType) * weight;
	}
	
	// getters and setters
	public ResourceType getResourceType() { return resourceType; }
	public void setResourceType(ResourceType resourceType) { this.resourceType = resourceType; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public double getWeight() { return weight; }
	public void setWeight(double weight) { this.weight = weight; }
	
	public boolean isTradeable() { return tradeable; }
	public void setTradeable(boolean tradeable) { this.tradeable = tradeable; }
}
