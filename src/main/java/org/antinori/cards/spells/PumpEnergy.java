package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class PumpEnergy extends BaseSpell {
	public PumpEnergy(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		ownerPlayer.incrementStrength(CardType.FIRE, 1);
		ownerPlayer.incrementStrength(CardType.AIR, 1);
		ownerPlayer.incrementStrength(CardType.EARTH, 1);
		ownerPlayer.incrementStrength(CardType.WATER, 1);
	}
}
