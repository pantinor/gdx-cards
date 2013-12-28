package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class MindMaster extends BaseCreature {
	public MindMaster(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {

		super.onSummoned();

		ownerPlayer.incrementStrength(CardType.FIRE, 1);
		ownerPlayer.incrementStrength(CardType.AIR, 1);
		ownerPlayer.incrementStrength(CardType.EARTH, 1);
		ownerPlayer.incrementStrength(CardType.WATER, 1);
		ownerPlayer.incrementStrength(CardType.OTHER, 1);
	}


}
