package com.countrysim.CountrySimulator.web.request;

public class RunSimulationRequest {
	private String generationMethod;
	private String configFileName;
	private String oracle;
	private int numberOfCountries;
	private int searchDepth;
	
	private double discountGamma;
	private int discountOffset;
	private int probabilityPenalty;
	
	private int[] availableLandRange;
	private int[] electronicsRange;
	private int[] electronicsWasteRange;
	private int[] farmLandRange;
	private int[] housingRange;
	private int[] housingWasteRange;
	private int[] metallicAlloysRange;
	private int[] metallicAlloysWasteRange;
	private int[] metallicElementsRange;
	private int[] populationRange;
	private int[] timberRange;
	
	/* GETTERS AND SETTERS */
	public String getGenerationMethod() { return generationMethod; }
	public void setGenerationMethod(String generationMethod) { this.generationMethod = generationMethod; }
	
	public String getConfigFileName() { return configFileName; }
	public void setConfigFileName(String configFileName) { this.configFileName = configFileName; }
	
	public String getOracle() { return oracle; }
	public void setOracle(String oracle) { this.oracle = oracle; }

	public int getNumberOfCountries() { return numberOfCountries; }
	public void setNumberOfCountries(int numberOfCountries) { this.numberOfCountries = numberOfCountries; }

	public int getSearchDepth() { return searchDepth; }
	public void setSearchDepth(int searchDepth) { this.searchDepth = searchDepth; }

	public double getDiscountGamma() { return discountGamma; }
	public void setDiscountGamma(double discountGamma) { this.discountGamma = discountGamma; }

	public int getDiscountOffset() { return discountOffset; }
	public void getDiscountOffset(int discountOffset) { this.discountOffset = discountOffset; }

	public int getProbabilityPenalty() { return probabilityPenalty; }
	public void setProbabilityPenalty(int probabilityPenalty) { this.probabilityPenalty = probabilityPenalty; }
	
	public int[] getAvailableLandRange() { return availableLandRange; }
	public void setAvailableLandRange(int[] availableLandRange) { this.availableLandRange = availableLandRange; }
	
	public int[] getElectronicsRange() { return electronicsRange; }
	public void setElectronicsRange(int[] electronicsRange) { this.electronicsRange = electronicsRange; }
	
	public int[] getElectronicsWasteRange() { return electronicsWasteRange; }
	public void setElectronicsWasteRange(int[] electronicsWasteRange) { this.electronicsWasteRange = electronicsWasteRange; }
	
	public int[] getFarmLandRange() { return farmLandRange; }
	public void setFarmLandRange(int[] farmLandRange) { this.farmLandRange = farmLandRange; }
	
	public int[] getHousingRange() { return housingRange; }
	public void setHousingRange(int[] housingRange) { this.housingRange = housingRange; }
	
	public int[] getHousingWasteRange() { return housingWasteRange; }
	public void setHousingWasteRange(int[] housingWasteRange) { this.housingWasteRange = housingWasteRange; }
	
	public int[] getMetallicAlloysRange() { return metallicAlloysRange; }
	public void setMetallicAlloysRange(int[] metallicAlloysRange) { this.metallicAlloysRange = metallicAlloysRange; }
	
	public int[] getMetallicAlloysWasteRange() { return metallicAlloysWasteRange; }
	public void setMetallicAlloysWasteRange(int[] metallicAlloysWasteRange) { this.metallicAlloysWasteRange = metallicAlloysWasteRange; }
	
	public int[] getMetallicElementsRange() { return metallicElementsRange; }
	public void setMetallicElementsRange(int[] metallicElementsRange) { this.metallicElementsRange = metallicElementsRange; }
	
	public int[] getPopulationRange() { return populationRange; }
	public void setPopulationRange(int[] populationRange) { this.populationRange = populationRange; }
	
	public int[] getTimberRange() { return timberRange; }
	public void setTimberRange(int[] timberRange) { this.timberRange = timberRange; }
}
