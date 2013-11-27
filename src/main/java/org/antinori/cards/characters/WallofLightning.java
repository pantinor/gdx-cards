package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class WallofLightning extends BaseCreature {
	public WallofLightning(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onAttack() {
		opponent.decrementLife(4, game, false);
		super.onAttack();
	}
}
