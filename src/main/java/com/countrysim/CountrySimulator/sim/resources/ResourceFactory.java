package com.countrysim.CountrySimulator.sim.resources;

public class ResourceFactory {
	public static Resource create(ResourceType resourceType) {
		switch(resourceType) {
			case Electronics:
				return new Electronics();
			case ElectronicsWaste:
				return new ElectronicsWaste();
			case Housing:
				return new Housing();
			case HousingWaste:
				return new HousingWaste();
			case MetallicAlloys:
				return new MetallicAlloys();
			case MetallicAlloysWaste:
				return new MetallicAlloysWaste();
			case MetallicElements:
				return new MetallicElements();
			case Population:
				return new Population();
			case Timber:
				return new Timber();
			default:
				return null;
		}
	}
}
