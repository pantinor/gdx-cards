package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Griffin extends BaseCreature {
	public Griffin(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {

		super.onSummoned();

		int air = ownerPlayer.getStrengthAir();
		if (air >= 5) {
			damagePlayer(opponent, 5);
		}
	}

}
