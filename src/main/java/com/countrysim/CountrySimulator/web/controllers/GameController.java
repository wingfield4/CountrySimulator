package com.countrysim.CountrySimulator.web.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.countrysim.CountrySimulator.sim.GameMaster;
import com.countrysim.CountrySimulator.web.response.WorldStateResponse;

@CrossOrigin(origins = "*")
@RestController
public class GameController {
	@GetMapping("/runSimulation")
	public WorldStateResponse newGame() {
		GameMaster gm = new GameMaster();
		gm.initialize();
		
		return new WorldStateResponse()
				.setCountryPool(gm.getCountryPool());
	}
}
