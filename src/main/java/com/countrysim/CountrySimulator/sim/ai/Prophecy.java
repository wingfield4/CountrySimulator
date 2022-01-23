package com.countrysim.CountrySimulator.sim.ai;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.countries.Country;

public class Prophecy {
	private List<Action> steps;
	private Country countryState;
	private double quality;
	
	//create one fresh
	public Prophecy(List<Action> steps, Country countryState) {
		this.steps = steps;
		this.countryState = countryState;
		
		//calculate this just the once
		this.quality = countryState.getResourcePool().getStateQuality();
	}
	
	//create an extension off another prophecy
	public Prophecy(Prophecy prevProphecy, Action nextAction) {
		steps = Stream.concat(prevProphecy.getSteps().stream(), Stream.of(nextAction))
				.collect(Collectors.toList());
		
		this.countryState = nextAction.tryExecute();
		quality = this.countryState.getResourcePool().getStateQuality();
	}
	
	//getters and setters
	public List<Action> getSteps() { return steps; }
	public Country getStateOfTheUnion() { return countryState; }
	public double getQuality() { return quality; }
}
