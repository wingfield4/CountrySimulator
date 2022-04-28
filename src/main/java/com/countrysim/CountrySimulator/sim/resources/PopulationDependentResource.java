package com.countrysim.CountrySimulator.sim.resources;

public abstract class PopulationDependentResource extends Resource {
	/**
	 * These resources should match population at some ratio
	 * higher doesn't get you much
	 * lower is very bad
	 */
	public double getStateQuality(ResourcePool resourcePool) {
		double IDEAL_RESOURCE_POPULATION_RATIO = 1;
		
		int populationCount = resourcePool.getResourceCount(ResourceType.Population);
		int resourceCount = resourcePool.getResourceCount(this.getResourceType());
		
		double populationValue = populationCount * IDEAL_RESOURCE_POPULATION_RATIO;
		
		double resourceDif = resourceCount - populationValue;
		double modifier = 0;

		if(resourceDif < 0) {
			//we have less resource than we need. Apply penalty
			//this penalty get expoonentally worse the larger the resource/population difference is
			double maxPenalty = populationValue*getWeight();
			double maxDif = populationValue;
			double dif = Math.min(-resourceDif, maxDif);
			double penalty = maxPenalty * Math.sqrt(Math.pow(dif/maxDif,3));
			
			modifier -= penalty;
		} else if(resourceDif > 0) {
			//we have more resource than we need. Apply bonus with diminishing returns
			//this bonus provides diminishing return up to a certain point past the ideal resource/population ratio
			//there's no possible penalty for "too much" resource, but bonus will stop after a point
			double maxBonus = populationValue*.25*getWeight();
			double maxDif = populationValue;
			double dif = Math.min(resourceDif, maxDif);
			double bonus = maxBonus * Math.sqrt(1 - Math.pow((dif/maxDif) - 1,2));
			
			modifier += bonus;
		}

		double baseValue = resourceCount * getWeight();
		return baseValue + modifier;
	}
}
