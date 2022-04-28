package com.countrysim.CountrySimulator.sim.utilities;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.countrysim.CountrySimulator.sim.resources.ResourceType;
import com.countrysim.CountrySimulator.sim.ai.OracleType;
import com.countrysim.CountrySimulator.sim.utilities.distributions.Distribution;
import com.countrysim.CountrySimulator.sim.utilities.distributions.NormalDistribution;

public class Config {
	public static GenerationMethod GENERATION_METHOD = GenerationMethod.Generated;
	public static String CONFIG_FILE_NAME;
	public static int NUMBER_OF_COUNTRIES = 10;
	public static int SEARCH_DEPTH = 30;
	public static int MAX_TURNS = 10;
	
	//expected utility function constants
	public static double DISCOUNT_GAMMA = .999;
	public static int DISCOUNT_OFFSET = 10;
	public static double PROBABILITY_PENALTY = -50;

	public static OracleType ORACLE_TYPE = OracleType.SIMPLE_BEAM_ORACLE;
	public static Map<String, OracleType> ORACLE_NAME_MAP = new HashMap<>(Map.ofEntries(
			makeEntry("PruneAtDepthOracle", OracleType.PRUNE_AT_DEPTH_ORACLE),
			makeEntry("SimpleBeamOracle", OracleType.SIMPLE_BEAM_ORACLE)
		));
	
	public static List<String> COUNTRY_NAMES = Arrays.asList(
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
	//the wrapper makes it mutable
	public static Map<ResourceType, Distribution> RESOURCE_DISTRIBUTIONS = new HashMap<>(Map.ofEntries(
			makeEntry(ResourceType.AvailableLand, new NormalDistribution(1, 40)),
			makeEntry(ResourceType.Electronics, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.ElectronicsWaste, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.FarmLand, new NormalDistribution(0, 40)),
			makeEntry(ResourceType.Housing, new NormalDistribution(1, 10)),
			makeEntry(ResourceType.HousingWaste, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.MetallicAlloys, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.MetallicAlloysWaste, new NormalDistribution(0, 0)),
			makeEntry(ResourceType.MetallicElements, new NormalDistribution(1, 200)),
			makeEntry(ResourceType.Population, new NormalDistribution(1, 40)),
			makeEntry(ResourceType.Timber, new NormalDistribution(1, 200))
		));
	
	public static Map<String, ResourceType> RESOURCE_NAME_MAP = new HashMap<>(Map.ofEntries(
			makeEntry("AvailableLand", ResourceType.AvailableLand),
			makeEntry("Electronics", ResourceType.Electronics),
			makeEntry("ElectronicsWaste", ResourceType.ElectronicsWaste),
			makeEntry("FarmLand", ResourceType.FarmLand),
			makeEntry("Housing", ResourceType.Housing),
			makeEntry("HousingWaste", ResourceType.HousingWaste),
			makeEntry("MetallicAlloys", ResourceType.MetallicAlloys),
			makeEntry("MetallicAlloysWaste", ResourceType.MetallicAlloysWaste),
			makeEntry("MetallicElements", ResourceType.MetallicElements),
			makeEntry("Population", ResourceType.Population),
			makeEntry("Timber", ResourceType.Timber)
		));

	private static Entry<ResourceType, Distribution> makeEntry(ResourceType resourceType, Distribution distribution) {
		return new AbstractMap.SimpleEntry<ResourceType, Distribution>(resourceType, distribution);
	}
	
	private static Entry<String, ResourceType> makeEntry(String resourceName, ResourceType resourceType) {
		return new AbstractMap.SimpleEntry<String, ResourceType>(resourceName, resourceType);
	}
	
	private static Entry<String, OracleType> makeEntry(String resourceName, OracleType oracleType) {
		return new AbstractMap.SimpleEntry<String, OracleType>(resourceName, oracleType);
	}
}
