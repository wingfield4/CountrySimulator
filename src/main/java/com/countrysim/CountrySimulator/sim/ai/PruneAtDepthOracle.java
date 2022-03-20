package com.countrysim.CountrySimulator.sim.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.actions.ActionFactory;
import com.countrysim.CountrySimulator.sim.countries.Country;

public class PruneAtDepthOracle implements Oracle {
	private Country country;
	
	public PruneAtDepthOracle(Country country) {
		this.country = country;
	}
	
	public Prophecy foresee(int maxDepth) {
		int PRUNING_DEPTH = 3;
		int SURVIVING_NODES = 1;
		
		int depth = 0;
		int depthSincePruning = 0;
		Queue<Prophecy> frontier = new LinkedList<Prophecy>();
		frontier.add(new Prophecy(country, new ArrayList<Action>(), new Country(country)));
		
		while(depth < maxDepth) {
			//do some kind of pruning at a certain point
			if(depthSincePruning >= PRUNING_DEPTH) {
				Prophecy[] newFrontier = new Prophecy[SURVIVING_NODES];
				
				List<Prophecy> sortedFrontier = frontier.stream()
					.sorted((x, y) -> Double.compare(y.getExpectedUtility(), x.getExpectedUtility()))
					.collect(Collectors.toList());
				
				for(var prophecy : sortedFrontier) {
					for(int i = 0; i < newFrontier.length; i++) {
						if(newFrontier[i] == null) {
							newFrontier[i] = prophecy;
							break;
						}
						
						
						if(prophecy.isRelated(newFrontier[i], depthSincePruning)) {
							//we're related and (because of ordering) have a worse quality than a step that already exists
							//so we definitely don't want to keep this node
							break;
						}
					}
					
					//if we've filled the array, break out of this loop, we're done
					if(newFrontier[newFrontier.length -1] != null)
						break;
				}
				
				frontier = Stream.of(newFrontier)
						.filter(prophecy -> prophecy != null)
						.collect(Collectors.toCollection(LinkedList::new));
				
				depthSincePruning = 0;
			}

			int startingSize = frontier.size();
			for(int i = 0; i < startingSize; i++) {
				Prophecy prevProphecy = frontier.poll();
				frontier.add(prevProphecy);

				if(prevProphecy.getLevel() == depth) {
					for(Action nextAction : ActionFactory.createFullActionList(prevProphecy.getFinalCountryState())) {
						if(nextAction.isValid())
							frontier.add(new Prophecy(country, prevProphecy, nextAction));
					}
				}
			}
				
			depth++;
			depthSincePruning++;
		}
		
		return frontier.stream()
				.max((x, y) -> Double.compare(x.getExpectedUtility(), y.getExpectedUtility()))
				.orElse(null);
	}
}
