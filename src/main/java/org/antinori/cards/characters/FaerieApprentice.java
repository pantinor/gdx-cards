package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class FaerieApprentice extends BaseCreature {
	public FaerieApprentice(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {
		int earth = ownerPlayer.getStrengthEarth();
		owner.incrementLife(earth, game);
	}

}
