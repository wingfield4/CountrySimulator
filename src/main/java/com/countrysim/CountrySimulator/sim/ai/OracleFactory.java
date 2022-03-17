package com.countrysim.CountrySimulator.sim.ai;

import com.countrysim.CountrySimulator.sim.countries.Country;

public class OracleFactory {
	public static Oracle create(OracleType oracleType, Country country) {
		switch(oracleType) {
			case BFRPOracle:
				return new BFRPOracle(country);
			default:
				return null;
		}
	}
}
