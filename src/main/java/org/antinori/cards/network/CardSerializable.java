package org.antinori.cards.network;

import java.io.Serializable;

import org.antinori.cards.Card;

public class CardSerializable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int slotIndex;
	private Card card;
	
	public CardSerializable(Card card, int slot) {
		this.card = card;
		this.slotIndex = slot;
	}
	
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public int getSlotIndex() {
		return slotIndex;
	}
	public void setSlotIndex(int slotIndex) {
		this.slotIndex = slotIndex;
	}

}
