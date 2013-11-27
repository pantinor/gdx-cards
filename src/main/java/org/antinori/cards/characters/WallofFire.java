package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class WallofFire extends BaseCreature {

	public WallofFire(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}
	
	public void onSummoned() {
		super.onSummoned();
		damageAll(false, 5);
	}
	
	public void onAttack() {
		//nothing
	}
}
