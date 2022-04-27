package com.countrysim.CountrySimulator.web.controllers;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.countrysim.CountrySimulator.web.response.WorldStateResponse;

@Controller
public class SocketController {
	@SendTo("/response/worldState")
	public WorldStateResponse worldState() throws Exception {
		return new WorldStateResponse();
	}
}
