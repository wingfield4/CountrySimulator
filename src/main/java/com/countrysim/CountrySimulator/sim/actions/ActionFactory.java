package com.countrysim.CountrySimulator.sim.actions;

import com.countrysim.CountrySimulator.sim.actions.transforms.*;
import com.countrysim.CountrySimulator.sim.countries.Country;

public class ActionFactory {
	public static Action create(ActionType actionType, Country country) {
		switch(actionType) {
			case MakeElectronics:
				return new MakeElectronics(country);
			case MakeHousing:
				return new MakeHousing(country);
			case MakeMetallicAlloys:
				return new MakeMetallicAlloys(country);
			default:
				return null;
		}
	}
}
