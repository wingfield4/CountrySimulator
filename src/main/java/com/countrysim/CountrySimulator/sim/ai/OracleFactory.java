package com.countrysim.CountrySimulator.sim.ai;

import com.countrysim.CountrySimulator.sim.countries.Country;

public class OracleFactory {
	public static Oracle create(OracleType oracleType, Country country) {
		switch(oracleType) {
			case PRUNE_AT_DEPTH_ORACLE:
				return new PruneAtDepthOracle(country);
			case SIMPLE_BEAM_ORACLE:
				return new SimpleBeamOracle(country);
			default:
				return null;
		}
	}
}
