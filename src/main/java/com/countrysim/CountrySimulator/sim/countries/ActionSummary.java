package com.countrysim.CountrySimulator.sim.countries;

import com.countrysim.CountrySimulator.sim.actions.Action;

public class ActionSummary {
	private String id;
	private String name;
	private String description;
	private double delta;
	
	public ActionSummary(String id, String name, String description, double delta) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.delta = delta;
	}
	
	public ActionSummary(Action action) {
		this.id = action.getId();
		this.name = action.getName();
		this.description = action.getDescription();
		this.delta = action.getStateQualityDelta();
	}
	
	public String getId() { return id; }
	public String getDescription() { return description; }
	public String getName() { return name; }
	public double getDelta() { return delta; }
}
