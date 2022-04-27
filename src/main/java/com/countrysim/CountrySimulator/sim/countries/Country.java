package com.countrysim.CountrySimulator.sim.countries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.actions.Transfer;
import com.countrysim.CountrySimulator.sim.ai.Oracle;
import com.countrysim.CountrySimulator.sim.ai.OracleFactory;
import com.countrysim.CountrySimulator.sim.ai.OracleType;
import com.countrysim.CountrySimulator.sim.ai.Prophecy;
import com.countrysim.CountrySimulator.sim.resources.ResourcePool;
import com.countrysim.CountrySimulator.sim.utilities.Config;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Country {
	private UUID id = UUID.randomUUID();
	private String name;
	private List<ActionSummary> actionHistory = new ArrayList<>();
	private boolean takingTurn = false;
	
	private Oracle oracle;
	private Prophecy prophecy;
	private ResourcePool resourcePool;
	private CountryPool countryPool;
	
	private long computeTime;
	
	public Country(String name) {
		this.name = name;
		resourcePool = new ResourcePool();
	}
	
	//create a copy, minus oracle/prophecy
	public Country(Country country) {
		name = country.getName();
		resourcePool = new ResourcePool(country.getResourcePool());
		countryPool = country.getCountryPool();
	}
	
	public boolean contemplateTrade(TradeProposal tradeProposal) {
		if(!tradeProposal.getTransfer().isValid(this))
			return false;
		
		var tradeProphecy = OracleFactory
				.create(Config.ORACLE_TYPE, tradeProposal.getTransfer().tryExecute(this))
				.foresee(Config.SEARCH_DEPTH);
		
		var noTradeProphecy = OracleFactory
				.create(Config.ORACLE_TYPE, this)
				.foresee(Config.SEARCH_DEPTH);
		
		return tradeProphecy.getStateQuality() > noTradeProphecy.getStateQuality();
	}
	
	public void planAhead() {
		oracle = OracleFactory.create(Config.ORACLE_TYPE, this);
		long start = System.nanoTime();
		prophecy = oracle.foresee(Config.SEARCH_DEPTH);
		long end = System.nanoTime();
		computeTime = end - start;
		System.out.println();
	}
	
	public TradeProposal takeTurn() {
		planAhead();
		return takeAction();
	}
	
	//return a transfer is proposing a trade
	private TradeProposal takeAction() {
		if(prophecy.getSteps().size() == 0) {
			//don't need these anymore, lets delete them for less data transmission
			prophecy = null;
			oracle = null;
			return null;
		}
		
		//copy the first step of our plan and execute it
		//TODO is necessary to clone? I forget how the plan works xd
		var action = prophecy.getSteps().get(0).clone(this);
		actionHistory.add(new ActionSummary(action));
		
		//don't need these anymore, lets delete them for less data transmission
		prophecy = null;
		oracle = null;
		
		//execute the action and return a potential trade proposal
		return action.execute();
	}
	
	public double getStateQuality() {
		return resourcePool.getStateQuality();
	}
	
	public void addActionSummary(ActionSummary actionSummary) { actionHistory.add(actionSummary); }
	
	//getters and setters
	public UUID getId() { return id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public List<ActionSummary> getActionHistory() { return actionHistory; }
	
	public boolean getTakingTurn() { return takingTurn; }
	public void setTakingTurn(boolean takingTurn) { this.takingTurn = takingTurn; }
	
	public Prophecy getProphecy() { return prophecy; }
	public ResourcePool getResourcePool() { return resourcePool; }
	
	@JsonIgnore
	public CountryPool getCountryPool() { return countryPool; }
	public void setCountryPool(CountryPool countryPool) { this.countryPool = countryPool; }
	
	public long getComputeTime() { return computeTime; }
}
