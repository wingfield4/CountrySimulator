package com.countrysim.CountrySimulator.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.countrysim.CountrySimulator.web.response.WorldStateResponse;

@Component
public class TownCrier {
	@Autowired
	private SimpMessagingTemplate template;
	
	public void sendMessage(WorldStateResponse worldStateResponse) {
		this.template.convertAndSend("/response/worldState", worldStateResponse);
	}
}
