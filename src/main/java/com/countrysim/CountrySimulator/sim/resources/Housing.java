package com.countrysim.CountrySimulator.sim.resources;

public class Housing extends Resource {
	public Housing() {
		setResourceType(ResourceType.Housing);
		setName("Housing");
		setWeight(15);
		setTradeable(false);
	}
	
	/**
	 * Housing should match population at some ratio
	 * higher doesn't get you much
	 * lower is very bad
	 */
	public double getStateQuality(ResourcePool resourcePool) {
		double IDEAL_HOUSING_POPULATION_RATIO = 1;
		
		int populationCount = resourcePool.getResourceCount(ResourceType.Population);
		int housingCount = resourcePool.getResourceCount(ResourceType.Housing);
		
		double populationValue = populationCount * IDEAL_HOUSING_POPULATION_RATIO;
		
		double housingDif = housingCount - populationValue;
		double modifier = 0;

		if(housingDif < 0) {
			//we have less housing than we need. Apply penalty
			//this penalty get expoonentally worse the larger the housing/population difference is
			double maxPenalty = populationValue*getWeight();
			double maxDif = populationValue;
			double dif = Math.min(-housingDif, maxDif);
			double penalty = maxPenalty * Math.sqrt(Math.pow(dif/maxDif,3));
			
			modifier -= penalty;
		} else if(housingDif > 0) {
			//we have more housing than we need. Apply bonus with diminishing returns
			//this bonus provides diminishing return up to a certain point past the ideal housing/population ratio
			//there's no possible penalty for "too much" housing, but bonus will stop after a point
			double maxBonus = populationValue*.25*getWeight();
			double maxDif = populationValue;
			double dif = Math.min(housingDif, maxDif);
			double bonus = maxBonus * Math.sqrt(1 - Math.pow((dif/maxDif) - 1,2));
			
			modifier += bonus;
		}

		double baseValue = housingCount * getWeight();
		return baseValue + modifier;
	}
}
