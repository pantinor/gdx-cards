package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class MindMaster extends BaseCreature {
	public MindMaster(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {

		super.onSummoned();

		ownerPlayer.incrementStrength(CardType.FIRE, 1);
		ownerPlayer.incrementStrength(CardType.AIR, 1);
		ownerPlayer.incrementStrength(CardType.EARTH, 1);
		ownerPlayer.incrementStrength(CardType.WATER, 1);
		ownerPlayer.incrementStrength(CardType.OTHER, 1);
	}


}
