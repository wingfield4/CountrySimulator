package com.countrysim.CountrySimulator.sim.actions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.countrysim.CountrySimulator.sim.actions.transforms.*;
import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class ActionFactory {
	public static Action createTransform(TransformType transformType, Country country) {
		switch(transformType) {
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
	
	public static Action createTransfer(TransferType transferType, Country country, ResourceType r1Type, ResourceType r2Type) {
		return new Transfer(country, transferType, r1Type, r2Type);
	}
	
	public static List<Action> createFullActionList(Country country) {
		//yeah it's a little ugly relax
		return Stream.concat(
				Stream.of(TransformType.values())
					.map(transformType -> createTransform(transformType, country)),
				Stream.of(TransferType.values())
					.flatMap(transferType -> {
						return Stream.of(ResourceType.values())
								.flatMap(r1Type -> {
									return Stream.of(ResourceType.values())
											.map(r2Type -> createTransfer(transferType, country, r1Type, r2Type));
								});
					}))
				.collect(Collectors.toList());
	}
}
