package org.antinori.cards;

import org.antinori.cards.network.Event;
import org.antinori.cards.network.NetworkEvent;

public class CardListener {
	
	private int index;
	private String id;
	
	public CardListener(int index, String id) {
		this.id = id;
		this.index = index;
	}
	
	public void setAttack(Card card, int value) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.CARD_SET_ATTACK, index, card.getName(), id);
			ne.setAttack(value);
			Cards.NET_GAME.sendEvent(ne);
		}
	}
	public void setLife(Card card, int value) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.CARD_SET_LIFE, index, card.getName(), id);
			ne.setLife(value);
			Cards.NET_GAME.sendEvent(ne);
		}
	}
	public void incrementLife(Card card, int value) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.CARD_INCR_LIFE, index, card.getName(), id);
			ne.setLifeIncr(value);
			Cards.NET_GAME.sendEvent(ne);
		}
	}
	public void decrementLife(Card card, int value) {
		if (Cards.NET_GAME != null) {
			NetworkEvent ne = new NetworkEvent(Event.CARD_DECR_LIFE, index, card.getName(), id);
			ne.setLifeDecr(value);
			Cards.NET_GAME.sendEvent(ne);
		}
	}

}
