package com.countrysim.CountrySimulator.sim.utilities.distributions;

public class NormalDistribution extends Distribution {
	private double minValue;
	private double maxValue;
	
	public NormalDistribution(double minValue, double maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public int getInt() {
		return DistributionUtilities.normallyDistribute((int)minValue, (int)maxValue);
	}
	
	public double getDouble() {
		return DistributionUtilities.normallyDistribute(minValue, maxValue);
	}
}
