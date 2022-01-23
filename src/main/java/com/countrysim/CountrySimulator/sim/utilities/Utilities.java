package com.countrysim.CountrySimulator.sim.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.countrysim.CountrySimulator.sim.utilities.distributions.DistributionUtilities;

public class Utilities {
	public static Random random = new Random();
	
	public static List<Integer> distributeResources(int countries, int resources) {
		//using accumulation here, which I don't like,
		//but I can't think of a better way to do it right now
		List<Integer> results = new ArrayList<Integer>();
		int remainingResources = resources;
		
		for(int i = 0; i < countries; i++) {
			int resourceChunk;
			
			if(i == countries - 1)
				resourceChunk = remainingResources;
			else
				resourceChunk = DistributionUtilities.normallyDistribute(0, remainingResources/(countries - i)*2);
			
			remainingResources -= resourceChunk;
			results.add(resourceChunk);
		}
		
		return results;
	}
}
