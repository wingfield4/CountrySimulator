package com.countrysim.CountrySimulator.sim.ai;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.utilities.Config;

public class Prophecy {
	private List<Action> steps;
	private Country country;
	private Country finalCountryState;

	private double stateQuality;
	private double undiscountedReward;
	private double discountedReward;
	private double probability;
	private double expectedUtility;
	
	//create one fresh
	public Prophecy(Country country, List<Action> steps, Country countryState) {
		this.country = country;
		this.steps = steps;
		this.finalCountryState = countryState;
		
		calculateValues();
	}
	
	//create an extension off another prophecy
	public Prophecy(Country country, Prophecy prevProphecy, Action nextAction) {
		this.country = country;
		steps = Stream.concat(prevProphecy.getSteps().stream(), Stream.of(nextAction))
				.collect(Collectors.toList());
		
		this.finalCountryState = nextAction.tryExecute();
		calculateValues();
	}
	
	private void calculateValues() {
		calculateStateQuality();
		calculateUndiscountedReward();
		calculateDiscountedReward();
		calculateProbability();
		calculateExpectedUtility();
	}
	
	private void calculateStateQuality() {
		stateQuality = finalCountryState.getResourcePool().getStateQuality();
	}
	
	private void calculateUndiscountedReward() {
		undiscountedReward = stateQuality - country.getResourcePool().getStateQuality();
	}
	
	private void calculateDiscountedReward() {
		int offset = Config.DISCOUNT_OFFSET; //don't affect score until past this many steps
		double gamma = Config.DISCOUNT_GAMMA;
		int adjustedLevel = Math.max(0, getLevel() - offset);
		
		discountedReward = Math.pow(gamma, adjustedLevel) * undiscountedReward;
	}
	
	private void calculateProbability() {
		probability = getSteps().stream()
				.mapToDouble(action -> action.getProbabilityOfSucces())
				.reduce(1.0, (probA, probB) -> probA * probB);
	}
	
	private void calculateExpectedUtility() {
		double penalty = Config.PROBABILITY_PENALTY;
		
		expectedUtility = (probability * discountedReward) + ((1 - probability) * penalty);
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
	
	public double getStateQuality() { return stateQuality; }
	public double getUndiscountedReward() { return undiscountedReward; }
	public double getDiscountedReward() { return discountedReward; }
	public double getProbability() { return probability; }
	public double getExpectedUtility() { return expectedUtility; }
}
