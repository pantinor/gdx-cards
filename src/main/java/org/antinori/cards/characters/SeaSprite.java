package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class SeaSprite extends BaseCreature {

	public SeaSprite(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}
	
	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		damagePlayer(true, 2);
		super.onAttack();
	}
}
