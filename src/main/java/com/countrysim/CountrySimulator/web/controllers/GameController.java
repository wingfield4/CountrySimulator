package com.countrysim.CountrySimulator.web.controllers;

import java.io.File;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.countrysim.CountrySimulator.sim.GameMaster;
import com.countrysim.CountrySimulator.sim.utilities.Config;
import com.countrysim.CountrySimulator.web.request.RunSimulationRequest;
import com.countrysim.CountrySimulator.web.response.WorldStateResponse;
import com.countrysim.CountrySimulator.web.services.ConfigService;

@CrossOrigin(origins = "*")
@RestController
public class GameController {
	@PostMapping("/runSimulation")
	public WorldStateResponse newGame(@RequestBody RunSimulationRequest request) {
		ConfigService.setConfig(request);
		
		GameMaster gm = new GameMaster();
			gm.initialize();
		
		return new WorldStateResponse()
				.setCountryPool(gm.getCountryPool());
	}
	
	@GetMapping("/getConfigFiles")
	public String[] getAvailableFiles() {
		//TODO some error handling here or something
		//also relative pathing please
		return new File("/Users/david/projects/CountrySimulator/src/main/resources/static/config").list();
	}
}
