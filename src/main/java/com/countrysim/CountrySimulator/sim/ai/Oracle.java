package com.countrysim.CountrySimulator.sim.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.countrysim.CountrySimulator.sim.actions.*;
import com.countrysim.CountrySimulator.sim.countries.Country;

public class Oracle {
	private Country country;
	
	public Oracle(Country country) {
		this.country = country;
	}
	
	public Prophecy foresee(int maxDepth) {
		int depth = 0;
		Queue<Prophecy> frontier = new LinkedList<Prophecy>();
		frontier.add(new Prophecy(new ArrayList<Action>(), country));
		
		while(depth < maxDepth) {
			//do some kind of culling at a certain point

			Queue<Prophecy> newFrontier = new LinkedList<Prophecy>();
			while(frontier.size() > 0) {
				Prophecy prevProphecy = frontier.poll();
				
				for(ActionType actionType : ActionType.values()) {
					Action nextAction = ActionFactory.create(actionType, country);
					if(nextAction.isValid())
						newFrontier.add(new Prophecy(prevProphecy, nextAction));
				}
			}
			frontier = newFrontier;
				
			depth++;
		}
		
		return frontier.stream()
				.max((x, y) -> Double.compare(x.getQuality(), y.getQuality()))
				.orElse(null);
	}
}
