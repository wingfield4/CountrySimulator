package com.countrysim.CountrySimulator.sim.countries;

import com.countrysim.CountrySimulator.sim.actions.Transfer;

public class TradeProposal {
	private Country proposingCountry;
	private Transfer transfer;
	
	public TradeProposal(Country proposingCountry, Transfer transfer) {
		this.proposingCountry = proposingCountry;
		this.transfer = transfer;
	}

	public void execute(Country acceptingCountry) {
		transfer.setRespondingCountry(acceptingCountry);
		transfer.finalize(proposingCountry, acceptingCountry);
	}
	
	public Transfer getTransfer() { return transfer; };
}
