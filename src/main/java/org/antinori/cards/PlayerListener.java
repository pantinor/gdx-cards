package org.antinori.cards;

import org.antinori.cards.network.Event;
import org.antinori.cards.network.NetworkEvent;

public class PlayerListener {
	
	private String id;
	
	public PlayerListener(String id) {
		this.id = id;
	}
	
	public void incrementLife(int value) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.PLAYER_INCR_LIFE, id);
			ne.setLifeIncr(value);
			//Cards.NET_GAME.sendEvent(ne);
		}
	}
	public void decrementLife(int value) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.PLAYER_DECR_LIFE, id);
			ne.setLifeDecr(value);
			//Cards.NET_GAME.sendEvent(ne);
		}
	}
	
	public void incrementStrengthAll(int value) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.PLAYER_INCR_STRENGTH_ALL, id);
			ne.setStrengthAffected(value);
			Cards.NET_GAME.sendEvent(ne);
		}
	}
	
	public void incrementStrength(int value, CardType type) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.PLAYER_INCR_STRENGTH, id);
			ne.setStrengthAffected(value);
			ne.setTypeStrengthAffected(type);
			Cards.NET_GAME.sendEvent(ne);
		}
	}
	
	public void decrementStrength(int value, CardType type) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.PLAYER_DECR_STRENGTH, id);
			ne.setStrengthAffected(value);
			ne.setTypeStrengthAffected(type);
			Cards.NET_GAME.sendEvent(ne);
		}
	}

}
