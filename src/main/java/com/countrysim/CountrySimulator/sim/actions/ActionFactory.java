package com.countrysim.CountrySimulator.sim.actions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.countrysim.CountrySimulator.sim.actions.transforms.*;
import com.countrysim.CountrySimulator.sim.countries.Country;
import com.countrysim.CountrySimulator.sim.resources.ResourceType;

public class ActionFactory {
	public static Action createTransform(TransformType transformType, Country country, int multiplier) {
		switch(transformType) {
			case MakeElectronics:
				return new MakeElectronics(country, multiplier);
			case MakeHousing:
				return new MakeHousing(country, multiplier);
			case MakeMetallicAlloys:
				return new MakeMetallicAlloys(country, multiplier);
			default:
				return null;
		}
	}
	
	public static Action createTransfer(TransferType transferType, Country country, ResourceType r1Type, ResourceType r2Type) {
		return new Transfer(country, transferType, r1Type, r2Type);
	}
	
	public static List<Action> createFullActionList(Country country) {
		//yeah it's a little ugly relax it does cool stuff
		return Stream.concat(
				Stream.of(TransformType.values())
					.flatMap(transformType -> IntStream.iterate(1, i -> i + 1)
							.mapToObj(multiplier -> createTransform(transformType, country, multiplier))
							.takeWhile(Action::isValid)),
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
