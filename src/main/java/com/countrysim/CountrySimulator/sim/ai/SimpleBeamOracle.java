package com.countrysim.CountrySimulator.sim.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.countrysim.CountrySimulator.sim.actions.Action;
import com.countrysim.CountrySimulator.sim.actions.ActionFactory;
import com.countrysim.CountrySimulator.sim.countries.Country;

public class SimpleBeamOracle implements Oracle {
	private Country country;
	
	public SimpleBeamOracle(Country country) {
		this.country = country;
	}
	
	public Prophecy foresee(int maxDepth) {
		int MAX_FRONTIER = 10000;
		
		int depth = 0;
		List<Prophecy> frontier = new ArrayList<Prophecy>();
		frontier.add(new Prophecy(country, new ArrayList<Action>(), new Country(country)));
		
		while(depth < maxDepth) {
			final int currentDepth = depth;
			frontier = frontier.parallelStream()
					.flatMap(prevProphecy -> {
						if(prevProphecy.getLevel() == currentDepth) {
							return Stream.concat(Stream.of(prevProphecy), 
									ActionFactory.createFullActionList(prevProphecy.getFinalCountryState())
										.stream()
										.filter(Action::isValid)
										.map(action -> new Prophecy(country, prevProphecy, action)));
						}
						
						return Stream.of(prevProphecy);
					})
					.sorted((x, y) -> Double.compare(y.getExpectedUtility(), x.getExpectedUtility()))
					.limit(MAX_FRONTIER)
					.collect(Collectors.toList());
				
			depth++;
		}
		
		return frontier.stream()
				.max((x, y) -> Double.compare(x.getExpectedUtility(), y.getExpectedUtility()))
				.orElse(null);
	}
}
