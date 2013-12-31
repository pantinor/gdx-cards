package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class AncientDragon extends BaseCreature {
	public AncientDragon(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		swapCard("BreatheFire", CardType.BEAST, "AncientDragon", owner);
		
		ownerPlayer.incrementStrength(CardType.FIRE, 1);
		ownerPlayer.incrementStrength(CardType.AIR, 1);
		ownerPlayer.incrementStrength(CardType.EARTH, 1);
		ownerPlayer.incrementStrength(CardType.WATER, 1);
		ownerPlayer.incrementStrength(CardType.BEAST, 1);

	}

	public void onDying() throws GameOverException {
		super.onDying();
		swapCard("AncientDragon", CardType.BEAST, "BreatheFire", owner);
	}
}
