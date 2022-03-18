package com.countrysim.CountrySimulator.sim.utilities;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.countrysim.CountrySimulator.sim.resources.ResourceType;
import com.countrysim.CountrySimulator.sim.ai.OracleType;
import com.countrysim.CountrySimulator.sim.utilities.distributions.Distribution;
import com.countrysim.CountrySimulator.sim.utilities.distributions.NormalDistribution;

public class Config {
	public static final int NUMBER_OF_COUNTRIES = 10;
	
	public static final OracleType ORACLE_TYPE = OracleType.BFRPOracle;
	public static final int SEARCH_DEPTH = 30;
	
	public static final List<String> COUNTRY_NAMES = Arrays.asList(
			"Atraria",
			"Braogua",
			"Cluic Stistan",
			"Doshaibia",
			"Eshua",
			"Flaosau",
			"Gloesal",
			"Hestary",
			"Iapreistan",
			"Jaspiadan",
			"Kobliyjan",
			"Logrein",
			"Mosnal",
			"Nuspil",
			"Oblesh",
			"Plaeles",
			"Qeskasil",
			"Refror",
			"Sheydor",
			"Teblua",
			"Uclesh",
			"Vaswioburg",
			"Wafloigua",
			"Xacreirus",
			"Yeskuidan",
			"Zoskausau"
		);
	
	//resources per-country distributions
	public static final Map<ResourceType, Distribution> RESOURCE_DISTRIBUTIONS = Map.ofEntries(
			makeEntry(ResourceType.Population, new NormalDistribution(1, 40)),
			makeEntry(ResourceType.Electronics, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.ElectronicsWaste, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.Housing, new NormalDistribution(1, 10)),
			makeEntry(ResourceType.HousingWaste, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.MetallicAlloys, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.MetallicAlloysWaste, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.MetallicElements, new NormalDistribution(1, 200)),
			makeEntry(ResourceType.Timber, new NormalDistribution(1, 200))
		);

	private static Entry<ResourceType, Distribution> makeEntry(ResourceType resourceType, Distribution distribution) {
		return new AbstractMap.SimpleEntry<ResourceType, Distribution>(resourceType, distribution);
	}
}
