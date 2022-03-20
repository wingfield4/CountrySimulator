package com.countrysim.CountrySimulator.sim.ai;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.countries.Country;

public class Prophecy {
	private List<Action> steps;
	private Country finalCountryState;
	private double quality;
	
	//create one fresh
	public Prophecy(List<Action> steps, Country countryState) {
		this.steps = steps;
		this.finalCountryState = countryState;
				
		this.quality = countryState.getResourcePool().getStateQuality();
	}
	
	//create an extension off another prophecy
	public Prophecy(Prophecy prevProphecy, Action nextAction) {
		steps = Stream.concat(prevProphecy.getSteps().stream(), Stream.of(nextAction))
				.collect(Collectors.toList());
		
		this.finalCountryState = nextAction.tryExecute();
		quality = this.finalCountryState.getResourcePool().getStateQuality();
	}
	
	public boolean isDescendant(Prophecy ancestor) {
		return ancestor.getLevel() == 0 || (getLevel() > ancestor.getLevel() &&
				ancestor.getSteps().get(ancestor.getLevel() - 1) == getSteps().get(ancestor.getLevel() - 1));
	}
	
	public boolean isAncestor(Prophecy descendant) {
		return getLevel() == 0 || (getLevel() < descendant.getLevel() &&
				descendant.getSteps().get(getLevel() - 1) == getSteps().get(getLevel() - 1));
	}
	 
	//ok the family analogy breaks down a little here but this makes sense I promise
	public boolean isSibling(Prophecy sibling) {
		return isCousin(sibling, 1);
	}
	
	public boolean isCousin(Prophecy cousin, int depthToCheck) {
		return getLevel() > depthToCheck && cousin.getLevel() > depthToCheck && getLevel() == cousin.getLevel() &&
				cousin.getSteps().get(getLevel() - depthToCheck - 1) == getSteps().get(getLevel() - depthToCheck - 1);
	}
	
	public boolean isRelated(Prophecy relative, int depthToCheck) {
		return isDescendant(relative) || isAncestor(relative) || isCousin(relative, depthToCheck);
	}
	
	//getters and setters
	public List<Action> getSteps() { return steps; }
	public int getLevel() { return getSteps().size(); }
	public Country getFinalCountryState() { return finalCountryState; }
	public double getQuality() { return quality; }
}
