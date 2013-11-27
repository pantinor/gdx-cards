package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Griffin extends BaseCreature {
	public Griffin(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {

		super.onSummoned();

		int air = ownerPlayer.getStrengthAir();
		if (air >= 5) {
			damagePlayer(false, 5);
		}
	}


}
