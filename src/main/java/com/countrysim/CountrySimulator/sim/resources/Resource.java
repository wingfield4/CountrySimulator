package com.countrysim.CountrySimulator.sim.resources;

public abstract class Resource {
	private ResourceType resourceType;
	private String name;
	private double weight = 1;
	
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
}
