package com.countrysim.CountrySimulator.sim.utilities.distributions;

import com.countrysim.CountrySimulator.sim.utilities.Utilities;

public class DistributionUtilities {
	/* NORMAL DISTRIBUTION */
    private static double calculateNormalDistribution(double mean, double stdDev)
    {
        //Hey David here, borrowed this code from here https://stackoverflow.com/a/218600
    	//case math is pretty hard sometimes
        double u1 = 1.0 - Utilities.random.nextDouble(); //uniform(0,1] random doubles
        double u2 = 1.0 - Utilities.random.nextDouble();
        double randStdNormal = Math.sqrt(-2.0 * Math.log(u1)) * Math.sin(2.0 * Math.PI * u2); //random normal(0,1)
        double randNormal = mean + stdDev * randStdNormal; //random normal(mean,stdDev^2)

        return randNormal;
    }
    
    // min/max are actually the 99.73% min/max (3 standard deviations)
    // but we'll force those values, thus screwing up normal distribution slightly
    // but it's close enough
    public static double normallyDistribute(double minValue, double maxValue)
    {
    	double mean = (minValue + maxValue) / 2.0;
        double stdDev = (mean - minValue) / 3;
        double value = calculateNormalDistribution(mean, stdDev);

        return Math.max(Math.min(value, maxValue), minValue);
    }

    public static int normallyDistribute(int minValue, int maxValue)
    {
        //basically casts everything to a double, but rounds and casts result back to int
        return (int)Math.round(normallyDistribute((double)minValue, (double)maxValue));
    }
    
    /* LINEAR DISTRIBUTION */
    public static double linearDistribution(double minValue, double maxValue)
    {
        return Utilities.random.nextDouble() * (maxValue - minValue) + minValue;
    }

    public static int linearDistribution(int minValue, int maxValue)
    {
        return Utilities.random.nextInt(maxValue - minValue) + minValue;
    }
}
