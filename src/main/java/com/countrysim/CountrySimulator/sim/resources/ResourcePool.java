package com.countrysim.CountrySimulator.sim.resources;

import java.util.HashMap;
import java.util.Map;

public class ResourcePool {
	private Map<ResourceType, Integer> valueMap = new HashMap<ResourceType, Integer>();
	private Map<ResourceType, Resource> resourceMap = new HashMap<ResourceType, Resource>();
	
	public ResourcePool() { }
	public ResourcePool(ResourcePool resourcePool) {
		resourceMap = resourcePool.getResourceMap();
		resourcePool.getValueMap()
			.entrySet()
			.forEach(entry -> valueMap.put(entry.getKey(), entry.getValue()));
	}
	
	public Integer setResource(ResourceType resourceType, int value) {
		resourceMap.putIfAbsent(resourceType, ResourceFactory.create(resourceType));
		return valueMap.putIfAbsent(resourceType, value);
	}
	
	public void adjustResource(ResourceType resourceType, int delta) {
		Integer oldValue = valueMap.get(resourceType);
		
		if(oldValue == null) //this probably shouldn't happen, but let's account for it anyway
			setResource(resourceType, delta);
		else
			valueMap.replace(resourceType, oldValue + delta);
	}
	
	public Resource getResource(ResourceType resourceType) {
		return resourceMap.get(resourceType);
	}
	
	//returns sum of state quality for each resource
	public double getStateQuality() {
		return resourceMap.values()
				.stream()
				.mapToDouble(resource -> resource.getStateQuality(this))
				.sum();
	}
	
	public Map<ResourceType, Integer> getValueMap() { return valueMap; }
	public Map<ResourceType, Resource> getResourceMap() { return resourceMap; }
}
