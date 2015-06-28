package org.antinori.cards.ai;

import org.antinori.cards.Card;

public class Move {
	
	private int slot;
	private Card card;
	
	public Move(int slot, Card card) {
		super();
		this.slot = slot;
		this.card = card;
	}

	public int getSlot() {
		return slot;
	}

	public Card getCard() {
		return card;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	

}
