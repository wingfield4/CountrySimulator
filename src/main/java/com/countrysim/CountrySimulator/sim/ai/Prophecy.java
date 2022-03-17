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
		
		//calculate this just the once
		this.quality = countryState.getResourcePool().getStateQuality();
	}
	
	//create an extension off another prophecy
	public Prophecy(Prophecy prevProphecy, Action nextAction) {
		steps = Stream.concat(prevProphecy.getSteps().stream(), Stream.of(nextAction))
				.collect(Collectors.toList());
		
		this.finalCountryState = nextAction.tryExecute();
		quality = this.finalCountryState.getResourcePool().getStateQuality();
	}
	
	public boolean isDescendant(Prophecy parent) {
		return parent.getLevel() == 0 || (getLevel() > parent.getLevel() &&
				parent.getSteps().get(parent.getLevel() - 1) == getSteps().get(parent.getLevel() - 1));
	}
	
	public boolean isSibling(Prophecy sibling) {
		return getLevel() > 1 && sibling.getLevel() > 1 && getLevel() == sibling.getLevel() &&
				sibling.getSteps().get(getLevel() - 2) == getSteps().get(getLevel() - 2);
	}
	
	//getters and setters
	public List<Action> getSteps() { return steps; }
	public int getLevel() { return getSteps().size(); }
	public Country getFinalCountryState() { return finalCountryState; }
	public double getQuality() { return quality; }
}
